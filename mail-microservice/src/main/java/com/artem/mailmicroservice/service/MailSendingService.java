package com.artem.mailmicroservice.service;

import com.artem.core.CreatedTaskEvent;
import com.artem.core.UserRegisteredEvent;
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
            helper.setSubject("Уведомление об регистрации");

            helper.setText("Вы успешно зарегистрированы в сервисе Task Tracker !", false);

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendCreatedTaskEmail(CreatedTaskEvent createdTaskEvent) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(createdTaskEvent.getEmailOwner());
            helper.setSubject("Добавлена новая задача");

            helper.setText("Добавлена новая задача: %s. Время добавления: %s"
                    .formatted(createdTaskEvent.getTitle(), createdTaskEvent.getCreatedAt()), false);

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
