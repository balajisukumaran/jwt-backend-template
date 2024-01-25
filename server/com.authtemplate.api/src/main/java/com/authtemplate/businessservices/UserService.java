package com.authtemplate.businessservices;

import com.authtemplate.businessservices.exceptions.AppException;
import com.authtemplate.businessservices.interfaces.IUserService;
import com.authtemplate.dataaccess.interfaces.IUserDAO;
import com.authtemplate.dtos.ResetPassword;
import com.authtemplate.entities.*;
import com.authtemplate.entities.enums.IdentifyBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.CharBuffer;
import java.util.Optional;

import com.authtemplate.dtos.CredentialsDto;
import com.authtemplate.dtos.SignUpDto;
import com.authtemplate.dtos.UserDto;
import com.authtemplate.mappers.UserMapper;
import lombok.RequiredArgsConstructor;

/**
 * User business services
 */
@RequiredArgsConstructor
@Service
public class UserService implements IUserService {

    /**
     * private user DAO
     */
    private IUserDAO userDAO;

    /**
     * private Password Encoder
     */
    private PasswordEncoder passwordEncoder;

    /**
     * constructor for user business service
     *
     * @param userDAO user data access object
     */
    @Autowired
    public UserService(IUserDAO userDAO) {
        this.userDAO = userDAO;
        passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * add a new user
     *
     * @param userDto user object
     */
    @Override
    @Transactional
    public void insert(UserDto userDto) {
        User user = userDAO.insert(UserMapper.toUser(userDto));
        UserMapper.toUserDto(user);
    }

    /**
     * authenticate a user
     *
     * @param credentialsDto credentials object
     * @return user object
     */
    @Override
    public UserDto login(CredentialsDto credentialsDto) {
        Optional<User> userFound = userDAO.findBy(credentialsDto.login(), IdentifyBy.Login);

        if(userFound == null || !userFound.isPresent())
            userFound = userDAO.findBy(credentialsDto.login(), IdentifyBy.Email);

        if (userFound != null && userFound.isPresent()) {
            User user = userFound.get();

            if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()),
                    user.getPassword())) {
                return UserMapper.toUserDto(user);
            }
            throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
        }

        throw new AppException("Unknown user", HttpStatus.NOT_FOUND);
    }

    /**
     * register user
     *
     * @param userDto user object
     * @return registered user
     */
    @Override
    public UserDto register(SignUpDto userDto) {
        Optional<User> optionalUser = userDAO.findBy(userDto.userName(), IdentifyBy.Login);

        if (optionalUser != null && optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.METHOD_NOT_ALLOWED);
        }

        User user = UserMapper.signUpToUser(userDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.password())));

        User savedUser = userDAO.insert(user);

        return UserMapper.toUserDto(savedUser);
    }

    /**
     * reset password
     *
     * @param resetPassword reset password object
     */
    @Override
    public void reRegister(ResetPassword resetPassword) {
        User user = UserMapper.resetPasswordToUser(resetPassword);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(resetPassword.password())));
        User savedUser = userDAO.insert(user);
        UserMapper.toUserDto(savedUser);
    }

    /**
     * find a user by login id
     *
     * @param login login id
     * @return user
     */
    @Override
    public UserDto findByLogin(String login) {
        Optional<User> user = userDAO.findBy(login, IdentifyBy.Login);
        if (user == null)
            return null;
        return user.map(UserMapper::toUserDto).orElse(null);
    }


    /**
     * find a user by email
     *
     * @param email user email
     * @return user object
     */
    @Override
    public UserDto findByEmail(String email) {
        Optional<User> user = userDAO.findBy(email, IdentifyBy.Email);

        if (user == null)
            return null;
        return user.map(UserMapper::toUserDto).orElse(null);
    }

    /**
     * find a user by reset token
     *
     * @param resetToken reset token
     * @return user object
     */
    @Override
    public UserDto findByResetToken(String resetToken) {
        Optional<User> user = userDAO.findBy(resetToken, IdentifyBy.ResetToken);

        if (user == null)
            return null;
        return user.map(UserMapper::toUserDto).orElse(null);
    }

}
