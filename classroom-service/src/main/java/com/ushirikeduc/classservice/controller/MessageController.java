package com.ushirikeduc.classservice.controller;

import Dto.ClassRoomEvent;

import Dto.ClassRoomEventEvent;
import com.ushirikeduc.classservice.model.ClassRoom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
@Slf4j
public record MessageController(
        @Qualifier("kafkaTemplateClassRoom")
        KafkaTemplate<String ,ClassRoomEvent> kafkaTemplateClassRoom,
        @Qualifier("kafkaTemplateClassRoomEvent")
        KafkaTemplate<String , ClassRoomEventEvent> kafkaTemplateClassRoomEvent

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
