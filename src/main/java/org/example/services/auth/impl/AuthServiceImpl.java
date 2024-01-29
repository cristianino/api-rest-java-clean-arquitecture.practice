package org.example.services.auth.impl;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.example.models.User;
import org.example.repositories.UserRepository;
import org.example.repositories.impl.UserRepositoryImpl;
import org.example.services.auth.AuthService;
import org.example.utils.JWTUtil;

public class AuthServiceImpl implements AuthService {
    public String authUser(String username, String password) {
        UserRepository userRepository = new UserRepositoryImpl();

        //Get user
        org.example.entities.User UserEntity = userRepository.getUserByUsername(username);

        if (UserEntity == null) throw new JWTVerificationException("user no found");

        //compare password hash
        if (!UserEntity.getPassword().equals(password)) throw new JWTVerificationException("invalid credentials");


        User userAuthenticated = new User(UserEntity.getUsername());
        userAuthenticated.setName(UserEntity.getName());
        userAuthenticated.setId(UserEntity.getId());
        userAuthenticated.setEmail(UserEntity.getEmail());
        return JWTUtil.generateToken(userAuthenticated);
    }
}
