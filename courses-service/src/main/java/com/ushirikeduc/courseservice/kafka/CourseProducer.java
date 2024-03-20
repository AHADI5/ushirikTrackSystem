package com.ushirikeduc.courseservice.kafka;

import Dto.CourseEvent;
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
public record CourseProducer(
        NewTopic courseTopic,
        KafkaTemplate<String , CourseEvent> kafkaTemplate
) {
    public void sendMessage(CourseEvent courseEvent) {
        log.info(String.format("course  Event => %s",
                courseEvent.toString()));
        //Creating the message
        Message<CourseEvent> message = MessageBuilder
                .withPayload(courseEvent)
                .setHeader(KafkaHeaders.TOPIC,courseTopic.name() )
                .build();
        kafkaTemplate.send(message);
    }
}