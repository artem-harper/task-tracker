package com.artem.taskapi.service;

import com.artem.taskapi.dto.AuthUserReqDto;
import com.artem.taskapi.dto.AuthUserRespDto;
import com.artem.taskapi.entity.User;
import com.artem.taskapi.exception.EmailAlreadyExistException;
import com.artem.taskapi.repository.UserRepository;
import com.artem.taskapi.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthUserRespDto registerUser(AuthUserReqDto authUserReqDto) {

        if (userRepository.findByEmail(authUserReqDto.getEmail()).isPresent()){
            throw new EmailAlreadyExistException();
        }

        User user = modelMapper.map(authUserReqDto, User.class);

        AuthUserRespDto authUserRespDto = modelMapper.map(userRepository.save(user), AuthUserRespDto.class);
        authUserRespDto.setToken(jwtTokenUtil.generateToken(user));

        return authUserRespDto;

    }
}
