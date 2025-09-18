package org.example.dto.requests.userRequest;

import lombok.Data;

@Data
public class RegisterRequest {
    String username;
    String email;
    String password;
}
