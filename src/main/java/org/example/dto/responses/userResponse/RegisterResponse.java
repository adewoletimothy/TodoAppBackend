package org.example.dto.responses.userResponse;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class RegisterResponse {
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private String message;
}
