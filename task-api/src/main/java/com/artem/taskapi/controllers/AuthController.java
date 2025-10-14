package com.artem.taskapi.controllers;

import com.artem.taskapi.dto.AuthUserReqDto;
import com.artem.taskapi.dto.AuthUserRespDto;
import com.artem.taskapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthUserRespDto> loginUser(@RequestBody AuthUserReqDto authUserReqDto) {

        AuthUserRespDto authUserRespDto = authService.loginUser(authUserReqDto);

        return ResponseEntity.ok()
                .header("Authorization ", authUserRespDto.getToken())
                .body(authUserRespDto);
    }
}
