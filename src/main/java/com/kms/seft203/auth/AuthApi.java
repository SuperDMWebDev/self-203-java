package com.kms.seft203.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthApi {

    private static final Map<String, User> DATA = new HashMap<>();

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        User user = new User(
                UUID.randomUUID().toString(),
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getFullName());

        DATA.put(user.getUsername(), user);

        // TODO: remove user's password
        return user;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        if ("admin".equals(request.getUsername()) && "Admin@123".equals(request.getPassword())) {
            LoginResponse loginResponse = new LoginResponse(
                    createJwtToken("admin", "Admin User"), "<refresh_token>");
            return ResponseEntity.ok(loginResponse);
        }

        User user = DATA.get(request.getUsername());
        if (user != null && user.getPassword().equals(request.getPassword())) {
            LoginResponse loginResponse = new LoginResponse(
                    createJwtToken(request.getUsername(), user.getFullName()), "<refresh_token>");
            return ResponseEntity.ok(loginResponse);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    private String createJwtToken(String user, String displayName) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("this is a secret");
            return JWT.create()
                    .withIssuer("kms")
                    .withClaim("user", user)
                    .withClaim("displayName", displayName)
                    .withExpiresAt(new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000)))
                    .sign(algorithm);
        } catch (JWTCreationException ex) {
            System.out.println(ex);
            return "";
        }
    }

    @PostMapping("/logout")
    public void logout(@RequestBody LogoutRequest request) {
    }
}
