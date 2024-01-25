package com.authtemplate.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import java.lang.String;

@AllArgsConstructor
@NoArgsConstructor
@Component
@Builder
@Data
public class ApiResponse {
    /**
     * private string message
     */
    private String message;
}
