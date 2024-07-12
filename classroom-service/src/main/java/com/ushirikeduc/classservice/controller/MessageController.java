package com.ushirikeduc.classservice.controller;

import Dto.ClassRoomEvent;

import Dto.ClassRoomEventEvent;
import Dto.HomeWorkAssignedEvent;
import com.ushirikeduc.classservice.model.ClassRoom;

import com.ushirikeduc.classservice.model.HomeWorkAssigned;
import com.ushirikeduc.classservice.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
@Slf4j
public record MessageController(
        @Qualifier("kafkaTemplateClassRoom")
        KafkaTemplate<String ,ClassRoomEvent> kafkaTemplateClassRoom,
        @Qualifier("kafkaTemplateClassRoomEvent")
        KafkaTemplate<String , ClassRoomEventEvent> kafkaTemplateClassRoomEvent,

        @Qualifier("kafkaTemplateHomeWork")
        KafkaTemplate<String , HomeWorkAssignedEvent> kafkaTemplateHomeWorkAssigned

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

    public void publishHomeWork(HomeWorkAssigned homeWorkAssigned) {
        HomeWorkAssignedEvent homeWorkAssignedEvent = new HomeWorkAssignedEvent();
        List<String> emails = new ArrayList<>();
        List<Integer> studentIDList = new ArrayList<>();
        //Getting  parent email , that will be used as recipient addresses
        for (Student student : homeWorkAssigned.getStudents()) {
            String email = student.getParentEmail();
            studentIDList.add((int) student.getStudentID());
            emails.add(email);
        }
        homeWorkAssignedEvent.setHomeWorkID(homeWorkAssigned.getHomeWorkAssignedID());
        homeWorkAssignedEvent.setConcern("classroomHomeWork");
        homeWorkAssignedEvent.setDueDate(homeWorkAssigned.getDateToBeDone().toString());
        homeWorkAssignedEvent.setRecipient(emails);
        homeWorkAssignedEvent.setStudentIDList(studentIDList);
        homeWorkAssignedEvent.setStatus(homeWorkAssigned.getHomeWorkStatus().name());
        kafkaTemplateHomeWorkAssigned.send("homework-assigned-created" , homeWorkAssignedEvent);
    }
}
//homework-assigned-created