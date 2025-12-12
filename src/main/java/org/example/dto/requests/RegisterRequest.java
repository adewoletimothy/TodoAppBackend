package org.example.dto.requests;

import lombok.Data;

@Data
public class RegisterRequest {
    String username;
    String email;
    String password;
}
