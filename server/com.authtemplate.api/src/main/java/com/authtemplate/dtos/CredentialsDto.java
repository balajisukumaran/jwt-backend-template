package com.authtemplate.dtos;

/**
 * credentials data transfer object
 */
public record CredentialsDto (String login, char[] password) { }