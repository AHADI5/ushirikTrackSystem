package com.ushirikeduc.student.controller;

import Dto.ParentEvent;
import Dto.StudentEvent;
import com.ushirikeduc.student.model.Parent;
import com.ushirikeduc.student.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
@Slf4j
public record MessageController(
        @Qualifier("kafkaTemplateStudent")
        KafkaTemplate<String ,StudentEvent> kafkaTemplateStudent,
        @Qualifier("kafkaTemplateParent")
        KafkaTemplate<String , ParentEvent> kafkaTemplateParent

        ) {

    public void publishStudent(Student student){
        StudentEvent studentEvent = new StudentEvent();
        studentEvent.setStudentID(student.getStudentID());
        studentEvent.setGender(student.getGender());
        studentEvent.setName(student.getName() + " " + student.getFirstName() + " " + student.getLastName());
        studentEvent.setGender(studentEvent.getGender());
        studentEvent.setClassID(student.getClassID());

        kafkaTemplateStudent.send("student-created",studentEvent);
        log.info(String.format("Student Event created  => %s ", studentEvent));
    }

    public void publishParent(Parent parent) {

        String password = generatePassword(parent);
        ParentEvent parentEvent= new ParentEvent();
        parentEvent.setFirstName(parent.getFirstName());
        parentEvent.setLastName(parent.getLastName());
        parentEvent.setEmail(parent.getEmail());
        parentEvent.setPassword(password);
        kafkaTemplateParent.send("parent-created",parentEvent);
        log.info(String.format("Parent event Created  => %s ", parentEvent));
    }
    private  String generatePassword(Parent parent) {
        //Generate a random number between 10 - 100
        int randomNumber = new Random().nextInt(91)+10;
        String firstName = parent.getFirstName();
        String lastName = parent.getLastName();
        //Combine teacher information with the generated random number to form teacher's password
        return firstName.substring(0,3) + lastName.substring(0,3) +randomNumber;
    }
}
