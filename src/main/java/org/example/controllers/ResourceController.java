package org.example.controllers;

import io.javalin.http.Context;
import org.example.models.User;
import org.example.utils.JWTUtil;

import java.util.Objects;

public class ResourceController {
    public static void getHello(Context ctx) {

        User user = ctx.attribute("user");
        assert user != null;
        ctx.result("Welcome, " + user.getUsername() + "!");
    }
}
