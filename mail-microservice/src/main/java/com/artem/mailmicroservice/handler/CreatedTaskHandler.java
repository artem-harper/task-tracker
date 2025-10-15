package com.artem.mailmicroservice.handler;

import com.artem.core.CreatedTaskEvent;
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
public class CreatedTaskHandler {

    private final MailSendingService mailSendingService;

    @KafkaHandler
    public void handle(CreatedTaskEvent createdTaskEvent) {
        mailSendingService.sendCreatedTaskEmail(createdTaskEvent);
    }
}
