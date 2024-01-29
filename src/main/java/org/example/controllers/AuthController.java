package org.example.controllers;

import com.google.gson.Gson;
import io.javalin.http.Context;
import org.example.requests.LoginRequest;
import org.example.responses.LoginResponse;
import org.example.services.AuthService;

public class AuthController {
    private static final Gson gson = new Gson();
    public static void login(Context ctx) {
        String requestBody = ctx.body();
        LoginRequest loginRequest = gson.fromJson(requestBody, LoginRequest.class);

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        LoginResponse loginResponse = new LoginResponse();

        loginResponse.token = AuthService.authUser(username, password);

        ctx.json(loginResponse);
    }
}


