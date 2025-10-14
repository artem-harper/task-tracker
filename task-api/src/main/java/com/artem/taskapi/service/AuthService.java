package com.artem.taskapi.service;

import com.artem.taskapi.dto.AuthUserReqDto;
import com.artem.taskapi.dto.AuthUserRespDto;
import com.artem.taskapi.entity.User;
import com.artem.taskapi.security.UserDetailsImpl;
import com.artem.taskapi.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthUserRespDto loginUser(AuthUserReqDto authUserReqDto) {


        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authUserReqDto.getEmail(), authUserReqDto.getPassword()));

        UserDetailsImpl userDetails = (UserDetailsImpl) authenticate.getPrincipal();

        AuthUserRespDto authUserRespDto = modelMapper.map(userDetails.getUser(), AuthUserRespDto.class);

        authUserRespDto.setToken(jwtTokenUtil.generateToken(userDetails));

        return authUserRespDto;
    }
}
