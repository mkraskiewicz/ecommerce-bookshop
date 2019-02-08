package com.mkraskiewicz.bookstore.service;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void SendPasswordReminder(String to, String subject, String content);
}
