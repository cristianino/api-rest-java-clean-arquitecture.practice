package org.example.controllers;

import com.google.gson.Gson;
import io.javalin.http.Context;
import org.example.models.User;
import org.example.requests.LoginRequest;
import org.example.requests.RegisterRequest;
import org.example.responses.LoginResponse;
import org.example.responses.RegisterResponse;
import org.example.services.auth.AuthService;
import org.example.services.auth.RegisterUserService;
import org.example.services.auth.impl.AuthServiceImpl;
import org.example.services.auth.impl.RegisterUserServiceImpl;
import org.example.utils.JWTUtil;

public class AuthController {

    private static final Gson gson = new Gson();
    public static void login(Context ctx) {
        AuthService authService = new AuthServiceImpl();
        String requestBody = ctx.body();
        LoginRequest loginRequest = gson.fromJson(requestBody, LoginRequest.class);

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        LoginResponse loginResponse = new LoginResponse();

        loginResponse.token = authService.authUser(username, password);

        ctx.json(loginResponse);
    }

    public static void register(Context ctx) {

        RegisterUserService registerUserService = new RegisterUserServiceImpl();
        String requestBody = ctx.body();
        RegisterRequest registerRequest = gson.fromJson(requestBody, RegisterRequest.class);

        User user = new User(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        String password = registerRequest.getPassword();

        User userRegister = registerUserService.register(user, password);

        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.user = userRegister;
        registerResponse.token = JWTUtil.generateToken(userRegister);
        ctx.json(registerResponse);
    }
}


