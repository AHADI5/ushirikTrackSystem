package com.ushirikeduc.student.controller;

import Dto.StudentEvent;
import com.ushirikeduc.student.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public record MessageController(KafkaTemplate<String , StudentEvent> kafkaTemplate) {

    public void publish(Student student){
        StudentEvent studentEvent = new StudentEvent();
        studentEvent.setStudentID(student.getStudentID());
        studentEvent.setGender(student.getGender());
        studentEvent.setName(student.getName() + " " + student.getFirstName() + " " + student.getLastName());
        studentEvent.setGender(studentEvent.getGender());
        studentEvent.setClassID(student.getClassID());

        kafkaTemplate.send("student-created",studentEvent);
        log.info(String.format("Student Event received in ClassRoom  => %s ", studentEvent));
    }
}
