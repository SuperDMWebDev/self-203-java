package com.kms.seft203.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kms.seft203.user.CustomUserDetails;
import com.kms.seft203.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.*;

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
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        return JWT.create().withIssuer(issuer)
                .withClaim("id",user.getId())
                .withClaim("username",user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + timeExpired))
                .sign(algorithm);

    }
//    public String generateToken(CustomUserDetails userDetails)
//    {
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + timeExpired);
//
//
//    }
    public String generateRefreshToken()
    {
        return UUID.randomUUID().toString();
    }

    public DecodedJWT decodedJWT(String token)
    {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        return JWT.require(algorithm).withIssuer(issuer).build().verify(token);
    }
    public Map<String,String> getInfoFromJWT(String token)
    {
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        System.out.println("payload "+payload);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> jsonMap = new HashMap<String,String>();
        try {
            jsonMap = mapper.readValue(payload, new TypeReference<Map<String,String>>(){
                });
            } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("jsonMap "+jsonMap.get("username"));
        return jsonMap;
    }



}
