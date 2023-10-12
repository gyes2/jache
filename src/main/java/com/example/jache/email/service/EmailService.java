package com.example.jache.email.service;

public interface EmailService {
    String sendVerificationCode(String chefName) throws Exception;
}
