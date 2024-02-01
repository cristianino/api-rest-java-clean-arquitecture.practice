package org.example.controllers;

import com.google.gson.Gson;
import io.javalin.http.Context;
import org.example.models.User;
import org.example.repositories.UserRepository;
import org.example.repositories.impl.UserRepositoryImpl;
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

    private static final UserRepository userRepository = new UserRepositoryImpl();

    public static void login(Context ctx) {
        try {

            AuthService authService = new AuthServiceImpl(userRepository);
            String requestBody = ctx.body();
            LoginRequest loginRequest = gson.fromJson(requestBody, LoginRequest.class);

            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();

            ctx.json(
                    LoginResponse.builder()
                            .token(authService.authUser(username, password))
                            .build()
            );
        } catch (Exception e) {
            ctx.result(e.getMessage());
        }
    }

    public static void register(Context ctx) {

        try {
            RegisterUserService registerUserService = new RegisterUserServiceImpl(userRepository);
            String requestBody = ctx.body();
            RegisterRequest registerRequest = gson.fromJson(requestBody, RegisterRequest.class);

            User userRegister = registerUserService.register(
                    User.builder()
                            .email(registerRequest.getEmail())
                            .name(registerRequest.getName())
                            .username(registerRequest.getUsername())
                            .build(),
                    registerRequest.getPassword()
            );

            ctx.json(
                    RegisterResponse.builder()
                            .token(JWTUtil.generateToken(userRegister))
                            .user(userRegister)
                            .build()
            );
        } catch (Exception e) {
            ctx.result(e.getMessage());
        }
    }
}


