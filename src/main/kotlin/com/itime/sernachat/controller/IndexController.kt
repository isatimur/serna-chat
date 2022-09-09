package com.itime.sernachat.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class IndexController {
    @GetMapping(*["", "/index"])
    fun index(): String {
        return "redirect:/chat/room"
    }
}
