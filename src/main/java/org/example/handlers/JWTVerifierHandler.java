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
        try {
            String token = Objects.requireNonNull(context.header("Authorization")).substring(7);
            JWTUtil.verify(token);

            context.attribute("user",
                    User.builder()
                            .name(JWTUtil.getNameToken())
                            .email(JWTUtil.getEmailToken())
                            .id(JWTUtil.getIdToken())
                            .username(JWTUtil.getUsernameToken())
                            .build()
            );

        } catch (JWTVerificationException | NullPointerException e){
            context.status(401).result("Token inválido. " + e.getMessage());
        }
    }
}
