package com.company.rabbitmq.consumer;

import com.company.dtos.MessageSendDTO;
import com.company.services.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMQConsumer {
    private final MailService mailService;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void consumeMessage(MessageSendDTO dto) {
        log.info("Json message received: {}", dto.toString());
        mailService.sendEmail(dto.email(), dto.code());
    }
}
