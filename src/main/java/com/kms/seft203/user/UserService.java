package com.kms.seft203.user;

import com.kms.seft203.exception.DataNotFoundException;
import com.kms.seft203.request.LoginRequest;
import com.kms.seft203.request.LogoutRequest;
import com.kms.seft203.request.RegisterRequest;
import com.kms.seft203.exception.ExistDataException;
import com.kms.seft203.response.LoginResponse;
import com.kms.seft203.jwt.JwtService;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;

@Service
public class  UserService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserJwtRepository userJwtRepository;

    @Autowired
    private JwtService jwtService;

    @Value("${mail.from-email}")
    private String fromEmail;
    //register user and return user
    public User register(RegisterRequest registerRequest, String request) throws ExistDataException, MessagingException, UnsupportedEncodingException {
        if(userRepository.findByUsername(registerRequest.getUsername()).isPresent())
        {
            throw new ExistDataException("username is already registered");
        }
        User user = User.createUser(registerRequest); // create user but password still not encode
        System.out.println("User created " + user.toString());
        if(user != null)
        {
            String randomCode = RandomString.make(64);
            user.setVerificationcode(randomCode);
            user.setEnabled(false);

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            sendVerificationEmail(user,request);
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
        return new LoginResponse(
                token,
                refreshToken
        );
    }

    public void logout(LogoutRequest logoutRequest) {
        UserJwt userJwt = userJwtRepository.findByUserId(logoutRequest.getUserId()).orElse(null);
        if(ObjectUtils.isNotEmpty(userJwt))
        {
            userJwtRepository.delete(userJwt);
        }
    }
    public User findUserByUserName(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        return user;
    }
    public void sendVerificationEmail(User user,String siteUrl) throws MessagingException, UnsupportedEncodingException {
        System.out.println("siteUrl "+ siteUrl);
        String toAddress=user.getEmail();
        String fromAddress= fromEmail;
        System.out.println("address send mail "+ fromAddress + " to "+toAddress);
        String senderName = fromEmail + "verification";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your company name.";  // need pass name, url
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        String verifyUrl = siteUrl + "/auth/verify?code="+user.getVerificationcode();

        content = content.replace("[[name]]", user.getFullName());
        content = content.replace("[[URL]]",verifyUrl);

        helper.setText(content, true);

        javaMailSender.send(message);

    }

    public Boolean verify(String verificationCode)
    {
        User user = userRepository.findByVerificationcode(verificationCode);

        if(user ==null || user.isEnabled())
        {
            return false;
        }
        user.setVerificationcode(null);
        user.setEnabled(true);
        userRepository.save(user);
        return true;
    }
    @Transactional
    public UserDetails loadUserById(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(()->{
            return new UsernameNotFoundException("User not found with id:" +id);
        });
        return new CustomUserDetails(user);
    }
}
