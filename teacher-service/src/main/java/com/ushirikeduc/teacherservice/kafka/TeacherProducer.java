package com.ushirikeduc.teacherservice.kafka;



import Dto.TeacherEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public record TeacherProducer(
        NewTopic topic,
        KafkaTemplate<String , TeacherEvent> kafkaTemplate
) {

    public void sendMessage(TeacherEvent teacherEvent) {
        log.info(String.format("Teacher Event => %s",
                teacherEvent.toString()));
        //Creating the message
        Message<TeacherEvent> message = MessageBuilder
                .withPayload(teacherEvent)
                .setHeader(KafkaHeaders.TOPIC,topic.name() )
                .build();
        kafkaTemplate.send(message);
    }
}
