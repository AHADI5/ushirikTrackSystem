package com.ushirikeduc.courseservice.kafka;

import Dto.ClassWorkEvent;
import Dto.CourseEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public record ClassWorkProducer(
        NewTopic classWorkTopic,
        KafkaTemplate<String , ClassWorkEvent> kafkaTemplate
) {
    public void sendMessage(ClassWorkEvent classWorkEvent) {
        log.info(String.format(" classWork Event => %s",
                classWorkEvent.toString()));
        //Creating the message
        Message<ClassWorkEvent> message = MessageBuilder
                .withPayload(classWorkEvent)
                .setHeader(KafkaHeaders.TOPIC,classWorkTopic.name() )
                .build();
        kafkaTemplate.send(message);
    }
}