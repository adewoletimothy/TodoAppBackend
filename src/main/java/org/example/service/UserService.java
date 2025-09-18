package org.example.service;

import org.example.data.models.Users;
import org.example.data.repositories.UserRepository;
import org.example.dto.requests.userRequest.LoginRequest;
import org.example.dto.requests.userRequest.RegisterRequest;
import org.example.dto.responses.userResponse.LoginResponse;
import org.example.dto.responses.userResponse.RegisterResponse;
import org.example.utils.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,  PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public RegisterResponse registerUser(RegisterRequest registerRequest) {
        if(userRepository.existsByUsername(registerRequest.getUsername())){
            throw new IllegalArgumentException("Username already exists");
        }if(userRepository.existsByEmail(registerRequest.getEmail())){
            throw new IllegalArgumentException("Email already exists");
        }
        Users user = Mapper.convertUserRequestToUser(registerRequest);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        Users savedUser = userRepository.save(user);
        return Mapper.convertUserToResponse(savedUser);
    }

    public LoginResponse loginUser(LoginRequest loginRequest) {
        logger.info("Attempting login for username: {}", loginRequest.getUsername());

        Users user = userRepository.findByUsername(loginRequest.getUsername());
        if (user == null) {
            logger.warn("Login failed: username {} not found", loginRequest.getUsername());
            throw new IllegalArgumentException("Username not found");
        }
        logger.info("User found: {}. Checking password...", user.getUsername());

        boolean passwordMatch = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
        logger.info("Password match result: {}", passwordMatch);

        if (!passwordMatch) {
            logger.warn("Login failed: wrong password for username {}", user.getUsername());
            throw new IllegalArgumentException("Wrong password");
        }

//        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
//            throw new IllegalArgumentException("Wrong password");
//        }
        logger.info("Login successful for user: {}", user.getUsername());
        return Mapper.convertUserToLoginResponse(user);
    }


    public void deleteUser(LoginRequest loginRequest){
        for(Users user : userRepository.findAll()) {
            if (user.getUsername().equals(loginRequest.getUsername()) &&
                    passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                userRepository.delete(user);
                return;
            }
        }
        throw new IllegalArgumentException("Wrong username or password");
    }

//    public void deleteUser(LoginRequest loginRequest) {
//        User user = userRepository.findByUsername(loginRequest.getUsername())
//                .orElseThrow(() -> new IllegalArgumentException("Wrong username or password"));
//
//        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
//            userRepository.delete(user);
//        } else {
//            throw new IllegalArgumentException("Wrong username or password");
//        }
//    }


}

