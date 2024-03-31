package com.ushirikeduc.courseservice.controller;

import Dto.ClassWorkEvent;
import Dto.CourseEvent;
import Dto.StudentEvent;
import com.ushirikeduc.courseservice.model.Course;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class MessageController {

    final KafkaTemplate<String , CourseEvent> kafkaTemplateCourse;
    final KafkaTemplate<String, ClassWorkEvent> kafkaTemplateClasswork;

    public MessageController(
            @Qualifier("kafkaTemplateCourse")
            KafkaTemplate<String, CourseEvent> kafkaTemplateCourse,
            @Qualifier("kafkaTemplateClasswork")
            KafkaTemplate<String, ClassWorkEvent> kafkaTemplateClasswork) {
        this.kafkaTemplateCourse = kafkaTemplateCourse;
        this.kafkaTemplateClasswork = kafkaTemplateClasswork;
    }

    public void publishCourse(Course newCourse){

        CourseEvent courseEvent = new CourseEvent() ;
        courseEvent.setCourseID(newCourse.getCourseID());
        courseEvent.setClassId(newCourse.getClassRoomID());
        courseEvent.setName(newCourse.getName());


        kafkaTemplateCourse.send("course-created",courseEvent);
        log.info(String.format("Course Event Created  => %s ", courseEvent));
    }


}
