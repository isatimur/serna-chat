package com.itime.sernachat.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .headers()
            .frameOptions().sameOrigin()
            .and()
//            .formLogin()
//            .and()
//            .antMatchers("/v1/api/chat/**").hasRole("USER")
            .authorizeRequests()
            .anyRequest().permitAll()
    }

//    @Throws(Exception::class)
//    override fun configure(auth: AuthenticationManagerBuilder) {
//        auth.inMemoryAuthentication()
//            .withUser("timur")
//            .password("{noop}1234")
//            .roles("USER")
//            .and()
//            .withUser("rumit")
//            .password("{noop}1234")
//            .roles("USER")
//            .and()
//            .withUser("guest")
//            .password("{noop}1234")
//            .roles("GUEST")
//    }

    @Bean
    fun customOpenAPI(): OpenAPI? {
        return OpenAPI()
            .components(
                Components()
                    .addSecuritySchemes(
                        "X-Private-Key",
                        SecurityScheme()
                            .type(SecurityScheme.Type.APIKEY)
                            .`in`(SecurityScheme.In.HEADER)
                            .name("X-Private-Key")
                    )
            ).addSecurityItem(SecurityRequirement().addList("X-Private-Key"))
    }


}
