package com.authtemplate.dataaccess.interfaces;

import com.authtemplate.entities.User;
import com.authtemplate.entities.enums.IdentifyBy;

import java.util.List;
import java.util.Optional;

/**
 * user data access object
 */
public interface IUserDAO {

    /**
     * get all users
     * @return users
     */
    List<User> findAll();

    /**
     * insert a user
     * @param user user to insert
     * @return inserted user
     */
    User insert(User user);

    /**
     * find a user by email, token or login id
     * @param value value to search by
     * @param identifyBy identify by login, email or token
     * @return user found
     */
    Optional<User> findBy(String value, IdentifyBy identifyBy);
}
