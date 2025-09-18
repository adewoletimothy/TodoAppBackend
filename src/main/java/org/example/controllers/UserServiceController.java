package org.example.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.data.models.Users;
import org.example.data.repositories.UserRepository;
import org.example.dto.requests.userRequest.LoginRequest;
import org.example.dto.requests.userRequest.RegisterRequest;
import org.example.dto.responses.userResponse.RegisterResponse;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/user")
public class UserServiceController {


    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserRepository userRepository;


    public UserServiceController(UserService userService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest user){
        RegisterResponse registerResponse = userService.registerUser(user);
        return ResponseEntity.ok(registerResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session){
        Users userInSession = userRepository.findByUsername(loginRequest.getUsername());
        if(!(userInSession == null) &&  passwordEncoder.matches(loginRequest.getPassword(),userInSession.getPassword())) {
            session.setAttribute("USER_ID", userInSession.getUserId());
        }
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody LoginRequest loginRequest) {
        try {
            userService.deleteUser(loginRequest);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
