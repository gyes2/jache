package com.example.jache.email;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration //스프링빈 설정 클래스임을 나타내는 어노테이션
@PropertySource("classpath:application.yml")
public class EmailConfig {

    @Value("587")
    private int port;
    @Value("587")
    private int socketPort;
    //@Value("${spring.mail.properties.mail.smtp.auth}")
    @Value("true")
    private boolean auth;
    @Value("true")
    private boolean starttls;
    @Value("true")
    private boolean startlls_required;
    @Value("false")
    private boolean fallback;
    @Value("jache2964@gmail.com")
    private String id;
    @Value("dmdbsnzpvlppzgho")
    private String password;
    @Value("smtp.gmail.com")
    private String host;

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setUsername(id);
        javaMailSender.setPassword(password);
        javaMailSender.setPort(587);
        javaMailSender.setJavaMailProperties(getMailProperties());
        javaMailSender.setDefaultEncoding("UTF-8");
        return javaMailSender;
    }
    private Properties getMailProperties()
    {
        Properties pt = new Properties();
        pt.put("spring.mail.properties.mail.smtp.socketFactory.port", socketPort);
        pt.put("mail.smtp.starttls.enable", "true");
        pt.put("mail.smtp.auth", "true");

        pt.put("spring.mail.properties.mail.smtp.starttls.required", startlls_required);
        pt.put("spring.mail.properties.mail.smtp.ssl.enable", "true");
        pt.put("spring.mail.properties.mail.smtp.socketFactory.fallback",fallback);
        pt.put("spring.mail.properties.mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        return pt;
    }
}