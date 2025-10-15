package com.artem.taskapi.service;

import com.artem.core.UserRegisteredEvent;
import com.artem.taskapi.dto.AuthUserReqDto;
import com.artem.taskapi.dto.AuthUserRespDto;
import com.artem.taskapi.dto.UserDto;
import com.artem.taskapi.entity.User;
import com.artem.taskapi.exception.EmailAlreadyExistException;
import com.artem.taskapi.repository.UserRepository;
import com.artem.taskapi.security.UserDetailsImpl;
import com.artem.taskapi.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Transactional
@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtTokenUtil jwtTokenUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final KafkaTemplate<Long, UserRegisteredEvent> kafkaTemplate;

    public AuthUserRespDto registerUser(AuthUserReqDto authUserReqDto) throws ExecutionException, InterruptedException {

        if (userRepository.findByEmail(authUserReqDto.getEmail()).isPresent()) {
            throw new EmailAlreadyExistException();
        }

        User user = modelMapper.map(authUserReqDto, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        AuthUserRespDto authUserRespDto = modelMapper.map(userRepository.save(user), AuthUserRespDto.class);

        authUserRespDto.setToken(jwtTokenUtil.generateToken(new UserDetailsImpl(user)));

        sendMessageToTopic(authUserRespDto.getId(), authUserRespDto.getEmail(), authUserReqDto.getPassword());

        return authUserRespDto;
    }

    public void sendMessageToTopic(Long id, String email, String password) throws ExecutionException, InterruptedException {
        UserRegisteredEvent userRegisteredEvent = new UserRegisteredEvent(email, password);

        SendResult<Long, UserRegisteredEvent> result = kafkaTemplate.send("EMAIL_SENDING_TASKS", id, userRegisteredEvent).get();

        log.info("topic: {}", result.getRecordMetadata().topic());
        log.info("partition: {}", result.getRecordMetadata().partition());
        log.info("offset: {}", result.getRecordMetadata().offset());

    }

    public UserDto findById(Long id) {

        return modelMapper.map(userRepository.findById(id), UserDto.class);
    }
}
