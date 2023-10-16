package com.example.jache.email.service;

import com.example.jache.user.service.ChefServiceImpl;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional

public class EmailServiceImpl implements EmailService{
    private final Logger LOGGER = LoggerFactory.getLogger(ChefServiceImpl.class);

    @Autowired(required=false)
    JavaMailSender emailSender;

    public static final String verifyCode = createKey();

    private MimeMessage createMessage(String to) throws Exception {
        LOGGER.info("[createMessage] 보내는 대상: {}, 인증코드:{}",to,verifyCode);
        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO,to);
        message.setSubject("자취생을 부탁해 이메일 인증");

        String msgg="";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> !! 자취생을 부탁해 !! </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다!<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= verifyCode+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("jieunlee0502@gmail.com","Jache"));

        return message;
    }

    private static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 4; i++) {
            key.append((rnd.nextInt(10)));
        }
        return key.toString();
    }

    @Override
    public String sendVerificationCode(String to) throws Exception {
        LOGGER.info("sendVerificationCode에서 to:{}",to);
        MimeMessage message = createMessage(to);
        LOGGER.info("to: {} message: {}",to, message);
        try{
            emailSender.send(message);
        }catch (MailException e){
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
        return verifyCode;
    }
}
