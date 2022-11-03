package com.kms.seft203;
import com.kms.seft203.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public JwtFilter jwtFilter()
    {
        return new JwtFilter();
    }
    @Override
    @Autowired
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        System.out.println("configure-class: SecurityConfig");
        httpSecurity.csrf().disable().authorizeRequests()
                // no use /** because it will map all requests
                .antMatchers("/app/*").permitAll()
                .antMatchers("/auth/*").permitAll()
                .anyRequest().authenticated();
//                .and().httpBasic()
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    //authen tication chap nhan het request

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }



}
