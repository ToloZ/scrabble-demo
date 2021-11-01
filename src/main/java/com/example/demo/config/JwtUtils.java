package com.example.demo.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public final class JwtUtils {
    public static Algorithm ALGORITHM = Algorithm.HMAC256("secret".getBytes());

    public static DecodedJWT getDecodedJwt(String authorizationHeader) {
        String token = authorizationHeader.substring("Bearer ".length());
        JWTVerifier verifier = JWT.require(ALGORITHM).build();
        return verifier.verify(token);
    }

    /**
     * @return a formatted token as String
     */
    public static String getTokenString(JWTCreator.Builder builder, String username, Integer minutes, String url) {
        return builder
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + minutes * 60 *1000))
                .withIssuer(url)
                .sign(JwtUtils.ALGORITHM);
    }

    /**
     * Parse JWT error message
     */
    public static void JwtError(Exception exception, HttpServletResponse response) throws IOException {
        log.error("Error loggin in: {}", exception.getMessage());
        response.setHeader("error", exception.getMessage());
        response.setStatus(HttpStatus.FORBIDDEN.value());
        //response.sendError(HttpStatus.FORBIDDEN.value());

        Map<String, String> error = new HashMap<>();
        error.put("error_message", exception.getMessage());
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }

    /**
     * Return tokens as JSON into the HttpResponse
     */
    public static void mapToResponse(String accessToken, String refreshToken, HttpServletResponse response) throws IOException {
        //response.setHeader("access_token", accessToken);
        //response.setHeader("refresh_token", refreshToken);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}
