package com.authtemplate.businessservices;

import com.authtemplate.businessservices.interfaces.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailService implements IEmailService {

    /**
     * autowire JavaMail Sender
     */
    @Autowired
    private JavaMailSender mailSender;

    /**
     * method to send email
     *
     * @param email for email
     */
    @Async
    public void sendEmail(SimpleMailMessage email) {
        mailSender.send(email);
    }
}