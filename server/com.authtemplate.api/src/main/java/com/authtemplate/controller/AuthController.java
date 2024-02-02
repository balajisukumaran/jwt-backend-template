package com.authtemplate.controller;

import com.authtemplate.businessservices.interfaces.IEmailService;
import com.authtemplate.businessservices.interfaces.IUserService;
import com.authtemplate.config.UserAuthenticationProvider;
import com.authtemplate.dtos.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.beans.factory.annotation.Value;

import java.net.URI;
import java.util.UUID;

@RestController
public class AuthController {

    private final IUserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;
    private final IEmailService emailService;

    @Value("${allowed.origin}")
    private String allowedOrigin;

    @Value("${system.email}")
    private String systemEmail;

    @Autowired
    public AuthController(IUserService userService, UserAuthenticationProvider userAuthenticationProvider, IEmailService emailService) {
        this.userService = userService;
        this.userAuthenticationProvider = userAuthenticationProvider;
        this.emailService = emailService;
    }

    /**
     * post mapping for login for user
     *
     * @param credentialsDto credentials Dto
     * @return ok status for user dto for response entity
     */
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid CredentialsDto credentialsDto) {
        UserDto userDto = userService.login(credentialsDto);
        userDto.setPassword(userAuthenticationProvider.createToken(userDto));
        return ResponseEntity.ok(userDto);
    }

    /**
     * post mapping for register
     *
     * @param user user
     * @return response entity created for suer
     */
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid SignUpDto user) {
        UserDto createdUser = userService.register(user);

        createdUser.setPassword(userAuthenticationProvider.createToken(createdUser));

        return ResponseEntity.created(URI.create("/users/" + createdUser.getUserId())).body(createdUser);
    }

    /**
     * post mapping for user by token
     *
     * @param jwtDto jwtDTO
     * @return ok in response in user auth
     */
    @PostMapping("/get/user")
    public ResponseEntity<UserDto> userByToken(@RequestBody @Valid JWTDto jwtDto) {
        return ResponseEntity.ok(userAuthenticationProvider.getUserByToken(jwtDto.JWT()));
    }

    /**
     * post mapping for reset
     *
     * @param resetDto reset DTO
     * @return error or ok in response in Alert DTO
     */
    @PostMapping("/reset")
    public ResponseEntity<AlertDto> reset(@RequestBody @Valid ResetDto resetDto) {
        UserDto user = userService.findByEmail(resetDto.getFlag());

        if (user == null) user = userService.findByLogin(resetDto.getFlag());

        if (user != null) {
            user.setResetToken(UUID.randomUUID().toString());

            // Save token to database
            userService.insert(user);

            // Email message
            SimpleMailMessage passwordResetEmail = getSimpleMailMessage(user);

            emailService.sendEmail(passwordResetEmail);

            AlertDto successDto = new AlertDto("Success", "A password reset link has been sent to " + user.getEmail());

            return ResponseEntity.ok(successDto);
        }

        AlertDto failedDto = new AlertDto("Error", "user not found");

        return ResponseEntity.ok(failedDto);
    }

    private SimpleMailMessage getSimpleMailMessage(UserDto user) {
        SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
        passwordResetEmail.setFrom(systemEmail);
        passwordResetEmail.setTo(user.getEmail());
        passwordResetEmail.setSubject("Password Reset Request");
        passwordResetEmail.setText("To reset your password, click the link below:\n"
                + allowedOrigin
                + "/auth/reset-password?token="
                + user.getResetToken());
        return passwordResetEmail;
    }

    /**
     * post mapping for reset password
     *
     * @param newPasswordDto new password DTO
     * @return user re register
     */
    @PostMapping("/new-password")
    public ResponseEntity<AlertDto> resetPassword(@RequestBody @Valid NewPasswordDto newPasswordDto) {

        // Find the user associated with the reset token
        UserDto user = userService.findByResetToken(newPasswordDto.getResetToken());

        // This should always be non-null, but we check just in case
        if (user != null) {

            ResetPassword resetPassword = new ResetPassword(user.getUserId()
                    , user.getFirstName()
                    , user.getLastName()
                    , user.getPhone()
                    , user.getEmail()
                    , user.getUserName()
                    , newPasswordDto.getNewPassword().toCharArray()
                    , null);

            userService.reRegister(resetPassword);

            return ResponseEntity.ok(new AlertDto("Success", "Password reset successful"));

        }

        return ResponseEntity.ok(new AlertDto("Error", "Invalid reset token."));
    }

    @GetMapping("/check")
    public String check(){
        return "Authentication Successful";
    }
}
