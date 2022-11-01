package com.kms.seft203.auth.config;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.kms.seft203.auth.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;


    private String getJwtTokenFromRequest(HttpServletRequest request)
    {
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
        {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //get only jwt token included in header
        String jwt = getJwtTokenFromRequest(request);
        if(jwt != null)
        {
            DecodedJWT decodedJwt = jwtService.decodedJWT(jwt);
            String payload  = decodedJwt.getPayload();
            System.out.println("payload "+payload);
        }
    }
}
