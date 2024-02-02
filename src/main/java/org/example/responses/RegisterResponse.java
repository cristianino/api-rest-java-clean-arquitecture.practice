package org.example.responses;

import lombok.Builder;
import lombok.Data;
import org.example.models.User;


@Builder
@Data
public class RegisterResponse {
    public static final String STATUS = "ok";
    private DataPayload data;
    private String status = STATUS;
    @Builder
    @Data
    public static class DataPayload {
        private String token;
        private User user;
    }
}

