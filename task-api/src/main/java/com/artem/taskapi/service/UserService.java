package com.artem.taskapi.service;

import com.artem.taskapi.dto.AuthUserDto;
import com.artem.taskapi.entity.User;
import com.artem.taskapi.exception.EmailAlreadyExistException;
import com.artem.taskapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public AuthUserDto registerUser(AuthUserDto authUserDto) {

        if (userRepository.findByEmail(authUserDto.getEmail()).isPresent()){
            throw new EmailAlreadyExistException();
        }

        User user = modelMapper.map(authUserDto, User.class);

        return modelMapper.map(userRepository.save(user), AuthUserDto.class);

    }
}
