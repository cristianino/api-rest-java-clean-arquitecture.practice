package org.example.controllers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.google.gson.Gson;
import io.javalin.http.Context;
import org.example.exceptions.RegisterUserException;
import org.example.models.User;
import org.example.repositories.UserRepository;
import org.example.repositories.impl.UserRepositoryImpl;
import org.example.requests.LoginRequest;
import org.example.requests.RegisterRequest;
import org.example.responses.FallbackResponse;
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
            String requestBody = ctx.body();

            RegisterRequest registerRequest = gson.fromJson(requestBody, RegisterRequest.class);
            registerRequest.validate(userRepository);

            RegisterUserService registerUserService = new RegisterUserServiceImpl(userRepository);

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
                            .data(
                                RegisterResponse.DataPayload.builder()
                                    .token(JWTUtil.generateToken(userRegister))
                                    .user(userRegister)
                                    .build()
                            )
                            .status(RegisterResponse.STATUS)
                            .build()
            );
        } catch (JWTVerificationException | RegisterUserException e) {
            ctx.status(400).json(FallbackResponse.builder()
                            .message(e.getMessage())
                            .status("Fallback")
                    .build());
        }
        catch (Exception e) {
            e.printStackTrace();
            // Return a custom error response
            ctx.status(500).json(FallbackResponse.builder()
                    .message("Internal Server Error: " + e.getMessage())
                    .status("Fallback")
                    .build());
        }
    }
}


