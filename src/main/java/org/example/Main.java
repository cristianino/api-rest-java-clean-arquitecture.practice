package org.example;

import io.javalin.Javalin;
import org.example.controllers.AuthController;
import org.example.controllers.ResourceController;
import org.example.handlers.JWTVerifierHandler;

import java.util.Objects;

public class Main {
    public static void main(String[] args) {

        Javalin app = Javalin.create().start(7070);

        app.before("/say-hello", new JWTVerifierHandler());
        app.before("/user", new JWTVerifierHandler());


        app.get("/ping", ctx -> ctx.result("pong"));

        app.post("/login", AuthController::login);

        app.post("/register", AuthController::register);

        app.get("/say-hello", ResourceController::getHello);

        app.get("/user", ctx -> ctx.json(Objects.requireNonNull(ctx.attribute("user"))));

    }
}