package com.authtemplate.dataaccess;

import com.authtemplate.dataaccess.interfaces.IUserDAO;
import com.authtemplate.entities.User;
import com.authtemplate.entities.enums.IdentifyBy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * user data access object
 */
@Repository
public class UserDAO implements IUserDAO {

    private final EntityManager entityManager;

    /**
     * user data object
     *
     * @param entityManager entity manager for persisting information
     */
    @Autowired
    public UserDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * get all users
     *
     * @return users
     */
    @Override
    public List<User> findAll() {
        // create a query
        TypedQuery<User> query = entityManager.createQuery("from User", User.class);

        // returns the result
        return query.getResultList();
    }

    /**
     * insert a user
     *
     * @param user user to insert
     * @return inserted user
     */
    @Transactional
    @Override
    public User insert(User user) {
        entityManager.merge(user);
        return user;
    }

    /**
     * find a user by email, token or login id
     *
     * @param value      value to search by
     * @param identifyBy identify by login, email or token
     * @return user found
     */
    @Override
    public Optional<User> findBy(String value, IdentifyBy identifyBy) {
        List<User> users = findAll();

        if (users != null) {
            Optional<List<User>> result = switch (identifyBy) {
                case Email -> Optional
                        .of(users.stream().filter(a -> Objects.equals(a.getEmail(), value))
                                .collect(Collectors.toList()));
                case Login -> Optional
                        .of(users.stream().filter(a -> Objects.equals(a.getUserName(), value))
                                .collect(Collectors.toList()));
                case ResetToken -> Optional
                        .of(users.stream().filter(a -> Objects.equals(a.getResetToken(), value))
                                .collect(Collectors.toList()));
            };

            if (result.stream().findAny().isPresent() && result.get().stream().count() > 0)
                return Optional.ofNullable(result.get().get(0));
        }

        return null;
    }
}
