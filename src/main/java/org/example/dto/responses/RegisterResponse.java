package org.example.dto.responses;

import lombok.Data;

@Data
public class RegisterResponse {
    private String id;
    private String username;
    private String password;
    private String email;
    private String message;
}
