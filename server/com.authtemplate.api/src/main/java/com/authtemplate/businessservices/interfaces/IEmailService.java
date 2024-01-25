package com.authtemplate.businessservices.interfaces;

import org.springframework.mail.SimpleMailMessage;

public interface IEmailService {
    /**
     * method to send email
     * @param email for email
     */
    public void sendEmail(SimpleMailMessage email);
}

