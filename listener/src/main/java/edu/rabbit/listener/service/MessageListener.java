package edu.rabbit.listener.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageListener {

    @RabbitListener(queues = "mq.messaging")
    public void onMessage(String message) {
        log.info("Received \"{}\"", message);
    }
}
