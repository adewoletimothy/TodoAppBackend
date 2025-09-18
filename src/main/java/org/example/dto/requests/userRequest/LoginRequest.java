package org.example.dto.requests.userRequest;

import lombok.Data;

@Data
public class LoginRequest {
    String username;
    String password;
}
