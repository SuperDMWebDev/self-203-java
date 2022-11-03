package com.kms.seft203;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AppApi {

    @Autowired
    private JavaMailSender mailSender;

    private final AppVersionRepository appVersionRepo;

    @GetMapping("/version")
    public AppVersion getCurrentVersion() {
        return appVersionRepo
                .findById(1L)
                .orElse(new AppVersion());
    }
    @GetMapping("/send-mail")
    public void sendPlainTextMail(Model model)
    {
        String from = "hadtnt71@gmail.com";
        String to = "hadtnt73@gmail.com";

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(to);
        message.setSubject("This is subject");
        message.setText("This is body");

        mailSender.send(message);
    }


}
