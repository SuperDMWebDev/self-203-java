package com.kms.seft203.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kms.seft203.dto.User;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private String jwtSecret;

    private String issuer;

    @Autowired
    public JwtService(@Value("${jwt.secret}") String jwtSecret,@Value("${jwt.issuer}") String issuer)
    {
        this.jwtSecret = jwtSecret;
        this.issuer = issuer;
    }
    private final static int timeExpired =3*60*60*1000;
    private final static int refreshTokenLength = 50;



    public String createUserToken(User user)
    {
        System.out.println("jwtSecret " +jwtSecret );
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        return JWT.create().withIssuer(issuer)
                .withClaim("id",user.getId())
                .withClaim("username",user.getUsername())
                .withClaim("password", user.getPassword())
                .withExpiresAt(new Date(System.currentTimeMillis() + timeExpired))
                .sign(algorithm);

    }
    public String generateRefreshToken()
    {
        return UUID.randomUUID().toString();
    }

    public DecodedJWT decodedJWT(String token)
    {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        return JWT.require(algorithm).withIssuer(issuer).build().verify(token);
    }



}
