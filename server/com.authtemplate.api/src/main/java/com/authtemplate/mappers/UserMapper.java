package com.authtemplate.mappers;

import com.authtemplate.dtos.ResetPassword;
import com.authtemplate.dtos.SignUpDto;
import com.authtemplate.dtos.UserDto;
import com.authtemplate.entities.User;

public class UserMapper {
    /**
     * static user dto method
     * @param user user object
     * @return updated user dto
     */
    public static UserDto toUserDto(User user) {
        UserDto userDto = null;

        if (user != null) {
            userDto = new UserDto();
            userDto.setUserId(user.getUserId());
            userDto.setPhone(user.getPhone());
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setUserName(user.getUserName());
            userDto.setEmail(user.getEmail());
            userDto.setResetToken(user.getResetToken());
            userDto.setPassword(user.getPassword());
        }
        return userDto;
    }

    /**
     * static to user
     * @param userDto object
     * @return updated user dto
     */
    public static User toUser(UserDto userDto) {
        User user = null;

        if (userDto != null) {
            user = new User();
            user.setUserId(userDto.getUserId());
            user.setPhone(userDto.getPhone());
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setUserName(userDto.getUserName());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user.setResetToken(userDto.getResetToken());
        }

        return user;
    }

    /**
     * method to sign up to user
     * @param signUpDto object
     * @return updaetd user obejct
     */
    public static User signUpToUser(SignUpDto signUpDto) {
        User user = null;

        if (signUpDto != null) {
            user = new User();
            user.setPhone(signUpDto.phone());
            user.setFirstName(signUpDto.firstName());
            user.setLastName(signUpDto.lastName());
            user.setUserName(signUpDto.userName());
            user.setEmail(signUpDto.email());
            user.setPassword(signUpDto.password().toString());
        }

        return user;
    }

    /**
     * method to reset password to user
     * @param resetPassword object
     * @return user object updated
     */
    public static User resetPasswordToUser(ResetPassword resetPassword) {
        User user = null;

        if (resetPassword != null) {
            user = new User();
            user.setUserId(resetPassword.userId());
            user.setPhone(resetPassword.phone());
            user.setFirstName(resetPassword.firstName());
            user.setLastName(resetPassword.lastName());
            user.setUserName(resetPassword.userName());
            user.setEmail(resetPassword.email());
            user.setResetToken(resetPassword.resetToken());
        }

        return user;
    }
}
