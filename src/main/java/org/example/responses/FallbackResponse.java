package org.example.responses;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class FallbackResponse {
    private String status = "fallback";
    private String message;
    private List<String> data = null;
}
