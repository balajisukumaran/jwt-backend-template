package com.authtemplate.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * user entity class
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "`user`")
public class User {

    /**
     * primary key and auto generated field user id
     */
    @Id
    @Column(name = "userid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    /**
     * first name column to store first name
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * field to store last name
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * phone number field
     */
    @Column(name = "phone")
    private String phone;

    /**
     * store user email address
     */
    @Column(name = "email")
    private String email;

    /**
     * username field for user to login
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * password to login
     */
    @Column(name = "password")
    private String password;

    /**
     * for reset password use reset token
     */
    @Column(name = "reset_token")
    private String resetToken;

}
