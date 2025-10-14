package com.artem.taskapi.service;

import com.artem.taskapi.entity.User;
import com.artem.taskapi.exception.UserNotExistException;
import com.artem.taskapi.repository.UserRepository;
import com.artem.taskapi.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> maybeUser = userRepository.findByEmail(email);

        if (maybeUser.isPresent()){
            throw new UserNotExistException();
        }

        return new UserDetailsImpl(maybeUser.get());
    }
}
