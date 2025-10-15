package com.artem.mailmicroservice.service;

import com.artem.mailmicroservice.handler.MessageHandler;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailSendingService {

    private final JavaMailSenderImpl mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendUserRegisteredEmail(String toEmail) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(toEmail + " вы зарегистрированы");

            helper.setText("Добро пожаловать в наш сервис! Вы успешно зарегистрированы.", false);

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
