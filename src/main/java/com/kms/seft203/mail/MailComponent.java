package com.kms.seft203.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

public class MailComponent {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail()
    {

    }
}
