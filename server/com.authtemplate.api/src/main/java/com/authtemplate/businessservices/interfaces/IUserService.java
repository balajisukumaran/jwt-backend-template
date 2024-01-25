package com.authtemplate.businessservices.interfaces;

import com.authtemplate.dtos.CredentialsDto;
import com.authtemplate.dtos.ResetPassword;
import com.authtemplate.dtos.SignUpDto;
import com.authtemplate.dtos.UserDto;

/**
 * Interface for user business services
 */
public interface IUserService {

    /**
     * add a new user
     *
     * @param userDto user object
     */
    void insert(UserDto userDto);

    /**
     * authenticate a user
     *
     * @param credentialsDto credentials object
     * @return user object
     */
    UserDto login(CredentialsDto credentialsDto);

    /**
     * register user
     *
     * @param userDto user object
     * @return registered user
     */
    UserDto register(SignUpDto userDto);

    /**
     * reset password
     *
     * @param resetPassword reset password object
     */
    void reRegister(ResetPassword resetPassword);

    /**
     * find a user by login id
     *
     * @param login login id
     * @return user
     */
    UserDto findByLogin(String login);

    /**
     * find a user by email id
     *
     * @param email email id
     * @return user
     */
    UserDto findByEmail(String email);

    /**
     * find a user by reset token
     *
     * @param resetToken reset token
     * @return user
     */
    UserDto findByResetToken(String resetToken);

}
