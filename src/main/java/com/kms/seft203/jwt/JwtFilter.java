package com.kms.seft203.jwt;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.kms.seft203.user.UserService;
import com.kms.seft203.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService customeUserDetailsService;


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
    protected void doFilterInternal
            (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //get only jwt token included in header
        String jwt = getJwtTokenFromRequest(request);
        if(StringUtils.hasText(jwt)) {
            Map<String,String> info = jwtService.getInfoFromJWT(jwt);
            User user = userService.findUserByUserName(info.get("username"));
            UserDetails userDetails = customeUserDetailsService.loadUserById(user.getId());
            if(userDetails!=null)
            {
                System.out.println("UserDetails "+userDetails);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null , userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                System.out.println("Authentication Token "+authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }
        filterChain.doFilter(request, response);

        }
}
