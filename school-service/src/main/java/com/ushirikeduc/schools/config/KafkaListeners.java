package com.ushirikeduc.schools.config;

import Dto.ClassRoomEvent;

import com.ushirikeduc.schools.service.ClassRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class KafkaListeners {

    private  static  final String CLASSROOM_TOPIC = "classroom-created";
    private final ClassRoomService classRoomService;

    public KafkaListeners(ClassRoomService classRoomService) {
        this.classRoomService = classRoomService;
    }

    @KafkaListener(
            topics = CLASSROOM_TOPIC,
            groupId = "classroom",
            containerFactory = "kafkaListenerContainerFactoryClassRoom"
    )

    void listener(ClassRoomEvent classRoomEvent) {
        log.info("Classroom Received in school  service %s" + classRoomEvent.toString() );
        classRoomService.registerClassRoom(classRoomEvent);


    }


}
