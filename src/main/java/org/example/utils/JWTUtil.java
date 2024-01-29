package org.example.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.example.models.User;

import java.util.Date;

public class JWTUtil {
    private static final String SECRET = "ccf0f2fd0f5f7268dfcaa01213e7664fbe1b453a24073fcc1e8a99757e68c1b9";
    private static DecodedJWT jwt;
    public static String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        long expirationDateMillis = 3600000; // 1 hour
        Date expirationDate = new Date(System.currentTimeMillis() + expirationDateMillis);
        return JWT.create()
                .withClaim("username", user.getUsername())
                .withClaim("name", user.getName())
                .withClaim("email", user.getEmail())
                .withClaim("id", user.getId())
                .withExpiresAt(expirationDate)
                .sign(algorithm);
    }

    public static String getNameToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("name").asString();
    }

    public static String getNameToken() {
        return jwt.getClaim("name").asString();
    }

    public static String getEmailToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("email").asString();
    }

    public static String getEmailToken() {
        return jwt.getClaim("email").asString();
    }


    public static int getIdToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("id").asInt();
    }

    public static int getIdToken() {
        return jwt.getClaim("id").asInt();
    }

    public static String getUsernameToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("username").asString();
    }

    public static String getUsernameToken() {
        return jwt.getClaim("username").asString();
    }
    public static void verify(String token) {
        jwt = JWT.require(Algorithm.HMAC256(SECRET))
                .build()
                .verify(token);

        Date currentDate = new Date();
        Date expirationDate = jwt.getExpiresAt();
        Date broadcastDate = jwt.getIssuedAt();
        if (!currentDate.before(expirationDate) && !currentDate.after(broadcastDate)) {
            throw new JWTVerificationException("El token ha expirado o aún no es válido");
        }
    }
}
