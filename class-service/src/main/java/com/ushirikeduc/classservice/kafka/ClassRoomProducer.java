package com.ushirikeduc.classservice.kafka;

import Dto.ClassRoomEvent;
import Dto.ClassWorkEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public record ClassRoomProducer(
        NewTopic classRoomTopic,
        KafkaTemplate<String , ClassRoomEvent> kafkaTemplate
) {
    public void sendMessage(ClassRoomEvent classRoomEvent) {
        log.info(String.format(" classWork Event => %s",
                classRoomEvent.toString()));
        //Creating the message
        Message<ClassRoomEvent> message = MessageBuilder
                .withPayload(classRoomEvent)
                .setHeader(KafkaHeaders.TOPIC,classRoomTopic.name() )
                .build();
        kafkaTemplate.send(message);
    }
}