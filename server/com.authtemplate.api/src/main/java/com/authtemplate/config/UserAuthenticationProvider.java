package com.authtemplate.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.authtemplate.businessservices.exceptions.AppException;
import com.authtemplate.dtos.UserDto;
import com.authtemplate.businessservices.UserService;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

/**
 * user authentication provider class
 */
@RequiredArgsConstructor
@Component
@Getter
@Setter
public class UserAuthenticationProvider {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    private final UserService userService;

    /**
     * one hour in milliseconds
     */
    private final int ONE_HOUR = 3600000;

    /**
     * code to be executed on initialization
     */
    @PostConstruct
    protected void init() {
        // this is to avoid having the raw secret key available in the JVM
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * create a token
     *
     * @param user user object
     * @return return token
     */
    public String createToken(UserDto user) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + ONE_HOUR);

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withSubject(user.getUserName())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("firstName", user.getFirstName())
                .withClaim("lastName", user.getLastName())
                .sign(algorithm);
    }

    /**
     * validate the token
     *
     * @param token token input
     * @return Authentication object
     */
    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm)
                .build();

        DecodedJWT decoded = verifier.verify(token);

        UserDto user = new UserDto();
        user.setUserName(decoded.getSubject());
        user.setFirstName(decoded.getClaim("firstName").asString());
        user.setLastName(decoded.getClaim("lastName").asString());

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

    /**
     * validate token strongly
     *
     * @param token token
     * @return authentication object
     */
    public Authentication validateTokenStrongly(String token) {
        UserDto user = getUserByToken(token);
        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

    /**
     * get the user by token
     *
     * @param token token
     * @return user object
     */
    public UserDto getUserByToken(String token) {

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm)
                .build();

        DecodedJWT decoded = verifier.verify(token);
        UserDto user = userService.findByLogin(decoded.getSubject());

        if (user == null) throw new AppException("Unknown user", HttpStatus.NOT_FOUND);

        return user;
    }
}
