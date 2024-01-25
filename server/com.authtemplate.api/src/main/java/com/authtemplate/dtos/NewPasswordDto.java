package com.authtemplate.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * new password data transfer object
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewPasswordDto {
    /**
     * string reset token
     */
    String resetToken;
    /**
     * string new password
     */
    String newPassword;
}
