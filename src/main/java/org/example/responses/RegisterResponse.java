package org.example.responses;

import lombok.Builder;
import lombok.Data;
import org.example.models.User;

@Builder
@Data
public class RegisterResponse {
    private String token;
    private User user;
}
