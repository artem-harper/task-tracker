package com.artem.taskapi.service;

import com.artem.taskapi.dto.AuthUserReqDto;
import com.artem.taskapi.dto.AuthUserRespDto;
import com.artem.taskapi.dto.UserDto;
import com.artem.taskapi.entity.User;
import com.artem.taskapi.exception.EmailAlreadyExistException;
import com.artem.taskapi.repository.UserRepository;
import com.artem.taskapi.security.UserDetailsImpl;
import com.artem.taskapi.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtTokenUtil jwtTokenUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthUserRespDto registerUser(AuthUserReqDto authUserReqDto) {

        if (userRepository.findByEmail(authUserReqDto.getEmail()).isPresent()){
            throw new EmailAlreadyExistException();
        }

        User user = modelMapper.map(authUserReqDto, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        AuthUserRespDto authUserRespDto = modelMapper.map(userRepository.save(user), AuthUserRespDto.class);
        authUserRespDto.setToken(jwtTokenUtil.generateToken(new UserDetailsImpl(user)));

        return authUserRespDto;
    }

    public UserDto findById(Long id){

        return modelMapper.map(userRepository.findById(id), UserDto.class);
    }
}
