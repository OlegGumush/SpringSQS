package com.spring.sqs;


import com.amazonaws.services.sqs.AmazonSQSAsync;
import io.awspring.cloud.messaging.core.QueueMessageChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class MessageSender {

    private static final String QUEUE_NAME = "testQueue";

    @Autowired
    private AmazonSQSAsync amazonSqs;

    public boolean send(final String messagePayload) {

        MessageChannel messageChannel = new QueueMessageChannel(amazonSqs, QUEUE_NAME);

        Message<String> msg = MessageBuilder.withPayload(messagePayload)
                .setHeader("sender", "app")
                .setHeaderIfAbsent("country", "IL")
                .build();

        boolean sentStatus = messageChannel.send(msg, 5000);

        log.info("message sent {}", sentStatus);
        return sentStatus;
    }
}
