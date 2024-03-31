//package com.ushirikeduc.student.kafka;
//
//import Dto.StudentEvent;
//import Dto.TeacherEvent;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.admin.NewTopic;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.support.KafkaHeaders;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.stereotype.Service;
//
//@Service
//@Slf4j
//public record StudentProducer(
//        NewTopic studentTopic,
//        KafkaTemplate<String , StudentEvent> kafkaTemplate
//) {
//    public void sendMessage(StudentEvent studentEvent) {
//        log.info(String.format("Student Event => %s",
//                studentEvent.toString()));
//        //Creating the message
//        Message<StudentEvent> message = MessageBuilder
//                .withPayload(studentEvent)
//                .setHeader(KafkaHeaders.TOPIC,studentTopic.name() )
//                .build();
//        kafkaTemplate.send(message);
//    }
//
//}
//
//
