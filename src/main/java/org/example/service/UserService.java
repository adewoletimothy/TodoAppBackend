package org.example.service;

import org.example.data.models.User;
import org.example.data.repositories.UserRepository;
import org.example.dto.requests.RegisterRequest;
import org.example.dto.responses.RegisterResponse;
import org.example.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User addUser(User user) {
        userRepository.save(user);
        return user;
    }

    public RegisterResponse register(RegisterRequest registerRequest) {
        if(userRepository.existsByUsername(registerRequest.getUsername())){
            throw new IllegalArgumentException("Username already exists");
        }
        if(userRepository.existsByEmail(registerRequest.getEmail())){
            throw new IllegalArgumentException("Email already exists");
        }
        User user = Mapper.convertUserRequestToUser(registerRequest);
        User savedUser = userRepository.save(user);
        return Mapper.convertUserToResponse(savedUser);
    }


}
