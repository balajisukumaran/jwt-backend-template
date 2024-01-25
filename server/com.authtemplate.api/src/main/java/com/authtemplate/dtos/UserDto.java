package com.authtemplate.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * user data transfer object
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    /**
     * integer user id
     */
    private int userId;

    /**
     * string phone number
     */
    private String phone;

    /**
     * string first name
     */
    private String firstName;

    /**
     * string last name
     */
    private String lastName;

    /**
     * string login
     */
    private String userName;

    /**
     * string token
     */
    private String password;

    /**
     * string email
     */
    private String email;

    /**
     * string reset token
     */
    private String resetToken;
}