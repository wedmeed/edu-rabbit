package edu.rabbit.sender.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageSender {

    RabbitTemplate template;

    volatile int count = 0;

    public MessageSender(@Qualifier("myRabbitCluster") ConnectionFactory factory) {
        this.template = new RabbitTemplate(factory);
        this.template.setExchange("ex.messaging");
    }

    @Scheduled(fixedRate = 100)
    synchronized void execute() {
        log.info("Send {}", count);
        template.convertAndSend("attempt № " + count++);
    }


}
