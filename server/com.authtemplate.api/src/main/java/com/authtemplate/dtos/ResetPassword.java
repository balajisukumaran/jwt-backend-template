package com.authtemplate.dtos;

/**
 * reset password data transfer object
 */
public record ResetPassword (int userId, String firstName, String lastName, String phone,String email, String userName, char[] password, String resetToken) { }