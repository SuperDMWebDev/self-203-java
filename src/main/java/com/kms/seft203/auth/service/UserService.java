package com.kms.seft203.auth.service;

import com.kms.seft203.auth.exception.DataNotFoundException;
import com.kms.seft203.auth.request.LoginRequest;
import com.kms.seft203.auth.request.LogoutRequest;
import com.kms.seft203.auth.request.RegisterRequest;
import com.kms.seft203.auth.exception.ExistDataException;
import com.kms.seft203.auth.repository.UserJwtRepository;
import com.kms.seft203.auth.repository.UserRepository;
import com.kms.seft203.auth.response.LoginResponse;
import com.kms.seft203.dto.User;
import com.kms.seft203.dto.UserJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Service
public class  UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserJwtRepository userJwtRepository;

    @Autowired
    private JwtService jwtService;
    //register user and return user
    public User register(RegisterRequest registerRequest) throws ExistDataException {
        if(userRepository.findByUsername(registerRequest.getUsername()).isPresent())
        {
            throw new ExistDataException("username is already registered");
        }
        if(userRepository.findByEmail(registerRequest.getEmail()).isPresent())
        {
            throw new ExistDataException("Email is already registered");
        }
        User user = User.createUser(registerRequest);
        System.out.println("User created " + user.toString());
        if(user != null)
        {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            return userRepository.save(user);
        }

        return null;

    }

    //login return token and refreshtoken
    public LoginResponse login(LoginRequest loginRequest) throws DataNotFoundException{
        // find user
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> {
            return new DataNotFoundException("Username can not be found");
        });
        //check password
        var encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(loginRequest.getPassword(),user.getPassword()))
        {
            throw new DataNotFoundException("Password do not correct");
        }
        // login successful
        // create access token
        var token =jwtService.createUserToken(user);
        var userJwt = userJwtRepository.findByUserId(user.getId());
        var refreshToken = userJwt.isEmpty() ? jwtService.generateRefreshToken(): userJwt.get().getRefreshToken();
        if(userJwt.isEmpty())
        {
            userJwtRepository.save(new UserJwt(0L,true,refreshToken,user));

        }
        System.out.println("SecurityContextHolder" + SecurityContextHolder.getContext());
        return new LoginResponse(
                token,
                refreshToken
        );
    }

    public void logout(LogoutRequest logoutRequest) {
        UserJwt userJwt = userJwtRepository.findByUserId(logoutRequest.getUserId()).orElse(null);
        assert userJwt != null : "User not found";
        userJwtRepository.delete(userJwt);
    }
}
