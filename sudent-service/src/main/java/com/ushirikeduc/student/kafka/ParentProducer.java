package com.ushirikeduc.student.kafka;

import Dto.ParentEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public record ParentProducer(
        NewTopic parentTopic,
        KafkaTemplate<String , ParentEvent> kafkaTemplate
) {
    public void sendMessage(ParentEvent parentEvent) {
        log.info(String.format("parent Event => %s",
                parentEvent.toString()));
        //Creating the message
        Message<ParentEvent> message = MessageBuilder
                .withPayload(parentEvent)
                .setHeader(KafkaHeaders.TOPIC,parentTopic.name() )
                .build();
        kafkaTemplate.send(message);
    }
}