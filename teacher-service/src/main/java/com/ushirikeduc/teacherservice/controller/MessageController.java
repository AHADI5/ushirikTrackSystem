package com.ushirikeduc.teacherservice.controller;

import Dto.StudentEvent;
import Dto.TeacherEvent;

import com.ushirikeduc.teacherservice.model.Teacher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
@Slf4j
public record MessageController(KafkaTemplate<String , TeacherEvent> kafkaTemplate) {

    public void publish(Teacher savedTeacher){
        String password = generatePassword(savedTeacher);
        //Set the teacher event
        TeacherEvent teacherEvent = new TeacherEvent();
        teacherEvent.setFirstName(savedTeacher.getFirstName());
        teacherEvent.setLastName(savedTeacher.getLastName());
        teacherEvent.setEmail(savedTeacher.getEmail());
        teacherEvent.setTeacherID(Math.toIntExact(savedTeacher.getId()));
        teacherEvent.setClassID(savedTeacher.getClassID());
        teacherEvent.setClassID(savedTeacher.getSchoolID());
        //Set the teacher-event  password
        teacherEvent.setPassword(password);

        kafkaTemplate.send("teacher-created",teacherEvent);
        log.info(String.format("New Teacher Event created  => %s ", teacherEvent));
    }
    private static String generatePassword(Teacher teacher) {
        //Generate a random number between 10 - 100
        int randomNumber = new Random().nextInt(91)+10;
        String firstName = teacher.getFirstName();
        String lastName = teacher.getLastName();
        //Combine teacher information with the generated random number to form teacher's password
        return firstName.substring(0,3) + lastName.substring(0,3) +randomNumber;

    }
}




