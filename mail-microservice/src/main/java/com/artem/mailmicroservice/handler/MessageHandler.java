package com.artem.mailmicroservice.handler;

import com.artem.core.UserRegisteredEvent;
import com.artem.mailmicroservice.service.MailSendingService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@KafkaListener(
        topics = "EMAIL_SENDING_TASKS"
)
@RequiredArgsConstructor
@Component
public class MessageHandler {

    private final MailSendingService mailSendingService;

    @KafkaHandler
    public void handle(UserRegisteredEvent userRegisteredEvent) {
        mailSendingService.sendUserRegisteredEmail(userRegisteredEvent.getEmail());
    }
}
