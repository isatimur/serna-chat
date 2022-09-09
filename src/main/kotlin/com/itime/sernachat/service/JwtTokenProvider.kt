package com.itime.sernachat.service

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.util.*

@Component
class JwtTokenProvider(val logger: Logger) {
    @Value("\${spring.jwt.secret}")
    private val secretKey: String? = null
    private val tokenValid = 1000L * 60 * 60
    fun generateToken(name: String?): String {
        val key = Keys.hmacShaKeyFor(secretKey!!.toByteArray(StandardCharsets.UTF_8))
        val now = Date.from(Instant.now())
        return Jwts.builder()
            .setId(name)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + tokenValid))
            .signWith(key)
            .compact()
    }

    fun getUserFromToken(token: String): String {
        return getClaims(token)!!.body.id
    }

    fun validateToken(token: String): Boolean {
        return getClaims(token) != null
    }

    private fun getClaims(token: String): Jws<Claims>? {
        return try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secretKey!!.toByteArray(StandardCharsets.UTF_8)))
                .build().parseClaimsJws(token)
        } catch (ex: SignatureException) {
            logger.error("Invalid JWT signature")
            throw ex
        } catch (ex: MalformedJwtException) {
            logger.error("Invalid JWT token")
            throw ex
        } catch (ex: ExpiredJwtException) {
            logger.error("Expired JWT token")
            throw ex
        } catch (ex: UnsupportedJwtException) {
            logger.error("Unsupported JWT token")
            throw ex
        } catch (ex: IllegalArgumentException) {
            logger.error("JWT claims string is empty")
            throw ex
        }
    }
}
