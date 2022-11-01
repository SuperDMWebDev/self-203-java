package com.kms.seft203.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.kms.seft203.auth.exception.DataNotFoundException;
import com.kms.seft203.auth.exception.ExistDataException;
import com.kms.seft203.auth.request.LoginRequest;
import com.kms.seft203.auth.request.LogoutRequest;
import com.kms.seft203.auth.request.RegisterRequest;
import com.kms.seft203.auth.response.LoginResponse;
import com.kms.seft203.auth.service.UserService;
import com.kms.seft203.dto.User;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/auth")
public class AuthApi {

    @Autowired
    private UserService userService;
    // temp data for save
    private static final Map<String, User> DATA = new HashMap<>();

    @GetMapping("/test")
    public String test()
    {
        return "test";
    }
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid RegisterRequest registerRequest) throws ExistDataException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(registerRequest));
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws DataNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(loginRequest));
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
    public ResponseEntity<Object>logout(@Valid @RequestBody LogoutRequest logoutRequest)
    {
        userService.logout(logoutRequest);
        return ResponseEntity.ok().body("Logout successfully");

    }

}
