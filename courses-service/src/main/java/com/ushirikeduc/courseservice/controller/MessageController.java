package com.ushirikeduc.courseservice.controller;

import Dto.ClassWorkEvent;
import Dto.CourseEvent;
import Dto.HomeWorkEvent;

import com.ushirikeduc.courseservice.dto.Workers;
import com.ushirikeduc.courseservice.model.ClassWork;
import com.ushirikeduc.courseservice.model.ClassworkType;
import com.ushirikeduc.courseservice.model.Course;

import com.ushirikeduc.courseservice.model.Homework;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


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
            classWorkEvent.setMaxScore(classWork.getMaxScore());
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


    public void publishNewHomework(Homework homework , List<Integer> studentIDs) {
        HomeWorkEvent  homeworkEvent = new HomeWorkEvent();
        homeworkEvent.setHomeWorkID((int) homework.getHomeWorkID());
        homeworkEvent.setTitle(homework.getTitle());
        homeworkEvent.setDateToBeDone(homework.getDateToBeDone().toString());
        homeworkEvent.setStudentIDs(studentIDs);

        kafkaTemplateHomework.send("homework-created" , homeworkEvent);
//        KafkaProducer<String , ClassWorkEvent> producer = createKafkaProducer();
//        producer.send(new ProducerRecord<String, ClassWorkEvent>("classwork-created", classWorkEvent));
//        producer.close();

//        log.info(String.format("New Classwork Available Event Created  => %s ", classWorkEvent));
    }


}
