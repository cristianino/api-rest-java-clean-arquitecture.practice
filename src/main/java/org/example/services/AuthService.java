package org.example.services;

import org.example.models.User;
import org.example.utils.JWTUtil;

public class AuthService {
    public static String authUser(String username, String password) {
        //get user and add at the context
        //User user = getUser(username);
        //compare password hash
        User userAuthenticated = new User(username);
        userAuthenticated.setName("Cristian Niño");
        userAuthenticated.setId(1);
        userAuthenticated.setEmail("test@test.com");
        return JWTUtil.generateToken(userAuthenticated);
    }
}
