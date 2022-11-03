package com.kms.seft203.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail()
    {

    }
}
