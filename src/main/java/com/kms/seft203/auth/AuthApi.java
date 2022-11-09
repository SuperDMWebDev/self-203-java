package com.kms.seft203.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.kms.seft203.exception.DataNotFoundException;
import com.kms.seft203.exception.ExistDataException;
import com.kms.seft203.request.LoginRequest;
import com.kms.seft203.request.LogoutRequest;
import com.kms.seft203.request.RegisterRequest;
import com.kms.seft203.response.LoginResponse;
import com.kms.seft203.user.UserService;
import com.kms.seft203.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
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
    public ResponseEntity<User> register(@RequestBody @Valid RegisterRequest registerRequest,HttpServletRequest request) throws ExistDataException, MessagingException, UnsupportedEncodingException {

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(registerRequest,getSiteUrl(request)));
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws DataNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(loginRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<Object>logout(@Valid @RequestBody LogoutRequest logoutRequest)
    {
        userService.logout(logoutRequest);
        return ResponseEntity.ok().body("Logout successfully");
    }
    @GetMapping("/verify")
    public String verifyUser(@RequestParam("code") String code)
    {
        System.out.println("get verify "+code);
        if(userService.verify(code))
        {
            return "verify_success";
        }
        else {
            return "verify_fail";
        }
    }
    public String getSiteUrl(HttpServletRequest request)
    {
        // /auth/verify
        return request.getRequestURL().toString().replace(request.getRequestURI(),"");
    }


}
