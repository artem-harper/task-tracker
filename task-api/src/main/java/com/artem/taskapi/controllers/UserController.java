package com.artem.taskapi.controllers;

import com.artem.taskapi.dto.AuthUserReqDto;
import com.artem.taskapi.dto.AuthUserRespDto;
import com.artem.taskapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity<AuthUserRespDto> registerUser(@RequestBody AuthUserReqDto authUserReqDto) {

        AuthUserRespDto authUserRespDto = userService.registerUser(authUserReqDto);

        return ResponseEntity.ok()
                .header("JwtToken", authUserRespDto.getToken())
                .body(authUserRespDto);
    }
}
