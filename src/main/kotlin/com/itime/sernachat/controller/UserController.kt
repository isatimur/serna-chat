package com.itime.sernachat.controller

import com.itime.sernachat.domain.User
import com.itime.sernachat.service.UserService
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*


@CrossOrigin(origins = ["http://localhost:3000"])
@Controller
@RequestMapping("/v1/api/users")
class UserController(val userService: UserService) {

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200")
    @ResponseBody
    fun getOrCreateUser(@RequestBody user: User): User {
        return userService.getOrCreate(user);
    }
}