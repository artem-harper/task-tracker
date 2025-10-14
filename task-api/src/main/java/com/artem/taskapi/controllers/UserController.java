package com.artem.taskapi.controllers;

import com.artem.taskapi.dto.AuthUserDto;
import com.artem.taskapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<AuthUserDto> registerUser(@RequestBody AuthUserDto authUserDto) {

        return ResponseEntity.ok(userService.registerUser(authUserDto));
    }
}
