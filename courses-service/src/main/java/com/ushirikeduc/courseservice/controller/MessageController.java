package com.ushirikeduc.courseservice.controller;

import Dto.ClassWorkEvent;
import Dto.CourseEvent;
import Dto.HomeWorkEvent;
import Dto.StudentEvent;

import com.ushirikeduc.courseservice.model.ClassWork;
import com.ushirikeduc.courseservice.model.ClassworkType;
import com.ushirikeduc.courseservice.model.Course;

import com.ushirikeduc.courseservice.model.Homework;
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
    final KafkaTemplate<String , HomeWorkEvent> kafkaTemplateHomework ;
//    private static  final String TOPIC = "classwork-created";

    public MessageController(
            @Qualifier("kafkaTemplateCourse")
            KafkaTemplate<String, CourseEvent> kafkaTemplateCourse,
            @Qualifier("kafkaTemplateClassWork")
            KafkaTemplate<String, ClassWorkEvent> kafkaTemplateClasswork ,
            @Qualifier("kafkaTemplateHomeWork")
            KafkaTemplate<String , HomeWorkEvent> kafkaTemplateHomework
            ) {
        this.kafkaTemplateCourse = kafkaTemplateCourse;
        this.kafkaTemplateClasswork = kafkaTemplateClasswork;
        this.kafkaTemplateHomework = kafkaTemplateHomework;
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
            classWorkEvent.setClassID(classWork.getClassID());
            classWorkEvent.setStartTime(classWork.getStartTime().toString());
            classWorkEvent.setEndTime(classWork.getEndTime().toString());
            classWorkEvent.setDateToBeDone(classWork.getDateToBeDone().toString());
            classWorkEvent.setDescription(classWork.getDescription());
            classWorkEvent.setTitle(createClassWorkTitle(classWork.getClassworkType() , classWork.getCourse().getName() ,classWork.getMaxScore()));


        kafkaTemplateClasswork.send("classwork-created" , classWorkEvent);
//        KafkaProducer<String , ClassWorkEvent> producer = createKafkaProducer();
//        producer.send(new ProducerRecord<String, ClassWorkEvent>("classwork-created", classWorkEvent));
//        producer.close();

//        log.info(String.format("New Classwork Available Event Created  => %s ", classWorkEvent));
    }

    public String createClassWorkTitle(ClassworkType classworkType, String courseName, long max) {
        switch (classworkType) {
            case QUIZ -> {
                return "Interrogation de " + courseName + " : " + max + " points";
            }
            case CLASSWORK -> {
                return "Devoir en classe de " + courseName + " : " + max + " points";
            }
            case TEST -> {
                return "Test de Contrôle de " + courseName + " : " + max + " points";
            }
        }
        return "no title provided";
    }


    public void publishNewHomework(Homework homework) {
        HomeWorkEvent  HomeworkEvent = new HomeWorkEvent();
        homework.setHomeWorkID(homework.getHomeWorkID());
        homework.setTitle(homework.getTitle());
        homework.setDateToBeDone(homework.getDateToBeDone());


        kafkaTemplateHomework.send("homework-created" , HomeworkEvent);
//        KafkaProducer<String , ClassWorkEvent> producer = createKafkaProducer();
//        producer.send(new ProducerRecord<String, ClassWorkEvent>("classwork-created", classWorkEvent));
//        producer.close();

//        log.info(String.format("New Classwork Available Event Created  => %s ", classWorkEvent));
    }


}
