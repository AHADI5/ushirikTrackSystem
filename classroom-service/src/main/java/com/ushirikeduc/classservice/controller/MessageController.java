package com.ushirikeduc.classservice.controller;

import Dto.ClassRoomEvent;

import com.ushirikeduc.classservice.model.ClassRoom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
@Slf4j
public record MessageController(

        KafkaTemplate<String ,ClassRoomEvent> kafkaTemplateClassRoom


        ) {

    public void publishStudent(ClassRoom classRoom){
        ClassRoomEvent classRoomEvent = new ClassRoomEvent();
        classRoomEvent.setClassesID(classRoom.getClassesID());
        classRoomEvent.setName(classRoom.getName());
        classRoomEvent.setLevel(classRoom.getLevel());
        classRoomEvent.setSchoolID(classRoom.getSchoolID());

        kafkaTemplateClassRoom.send("classroom-created",classRoomEvent);
        log.info(String.format("Student Event created  => %s ", classRoomEvent));
    }


}
