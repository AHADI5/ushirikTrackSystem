package com.ushirikeduc.courseservice.controller;

import Dto.ClassWorkEvent;
import Dto.CourseEvent;
import Dto.StudentEvent;

import com.ushirikeduc.courseservice.model.ClassWork;
import com.ushirikeduc.courseservice.model.Course;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.management.ObjectName;
import java.util.Map;




@Service
@Slf4j
public class MessageController {

    final KafkaTemplate<String , CourseEvent> kafkaTemplateCourse;
    final KafkaTemplate<String, ClassWorkEvent> kafkaTemplateClasswork;
//    private static  final String TOPIC = "classwork-created";

    public MessageController(
            @Qualifier("kafkaTemplateCourse")
            KafkaTemplate<String, CourseEvent> kafkaTemplateCourse,
            @Qualifier("kafkaTemplate")
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

    public void publishNewClasswork(ClassWork classWork) {
            ClassWorkEvent  classWorkEvent = new ClassWorkEvent();
            classWorkEvent.setCourseID(classWork.getCourse().getCourseID());
            classWorkEvent.setCourseName(classWork.getCourse().getName());
            classWorkEvent.setClassWorkID(classWork.getClassWorkID());
            classWorkEvent.setClassWorkType(classWork.getClassworkType().toString());

            classWorkEvent.setTitle(classWork.getName());

        kafkaTemplateClasswork.send("classwork-created" , classWorkEvent);
//        KafkaProducer<String , ClassWorkEvent> producer = createKafkaProducer();
//        producer.send(new ProducerRecord<String, ClassWorkEvent>("classwork-created", classWorkEvent));
//        producer.close();

//        log.info(String.format("New Classwork Available Event Created  => %s ", classWorkEvent));
    }


}
