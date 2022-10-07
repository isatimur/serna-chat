package com.itime.sernachat.controller

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class IndexController {
    @GetMapping(*["", "/index"])
    fun index(): ResponseEntity<String> {
        return ResponseEntity.ok("")
    }
}
