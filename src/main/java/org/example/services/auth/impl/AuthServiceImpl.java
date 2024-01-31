package org.example.services.auth.impl;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.example.repositories.UserRepository;
import org.example.repositories.impl.UserRepositoryImpl;
import org.example.services.auth.AuthService;
import org.example.usecase.UserTransformer;
import org.example.usecase.impl.RetrieveUserImpl;
import org.example.utils.JWTUtil;

public class AuthServiceImpl implements AuthService {
    public String authUser(String username, String password) {
        UserRepository userRepository = new UserRepositoryImpl();
        UserTransformer userTransformer  = new RetrieveUserImpl();

        //Get user
        org.example.entities.User userEntity = userRepository.getUserByUsername(username);

        if (userEntity == null) throw new JWTVerificationException("user no found");

        //compare password hash
        if (!userEntity.getPassword().equals(password)) throw new JWTVerificationException("invalid credentials");


        return JWTUtil.generateToken(
                userTransformer.apply(userEntity)
        );
    }
}
