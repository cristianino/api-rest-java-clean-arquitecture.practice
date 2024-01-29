package org.example.handlers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.models.User;
import org.example.utils.JWTUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class JWTVerifierHandler implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        String token = Objects.requireNonNull(context.header("Authorization")).substring(7);

        try {
            JWTUtil.verify(token);

            User user = new User(JWTUtil.getUsernameToken());
            user.setId(JWTUtil.getIdToken());
            user.setEmail(JWTUtil.getEmailToken());
            user.setName(JWTUtil.getNameToken());


            context.attribute("user", user);

        } catch (JWTVerificationException e){
            context.status(401).result("Token inválido. " + e.getMessage());
        }
    }
}
