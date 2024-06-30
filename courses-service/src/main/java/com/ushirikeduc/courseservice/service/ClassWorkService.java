package com.ushirikeduc.courseservice.service;

import Dto.ClassWorkEvent;
import com.ushirikeduc.courseservice.controller.MessageController;
import com.ushirikeduc.courseservice.dto.*;

import com.ushirikeduc.courseservice.model.ClassWork;
import com.ushirikeduc.courseservice.model.ClassworkType;
import com.ushirikeduc.courseservice.model.Course;
import com.ushirikeduc.courseservice.repository.ClassWorkRepository;
import com.ushirikeduc.courseservice.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public record ClassWorkService (
        CourseRepository courseRepository,
        ClassWorkRepository classWorkRepository,

        MessageController messageController ,
        HomeWorkService homeWorkService

) {

    public ClassWorkRegistrationResponse registerNewClassWork(int courseID, ClassWorkRegistrationRequest request) {
       //Setting classWork type , based on teacher wish
        ClassworkType classworkType = null;
        switch (request.type()) {
            case "Quiz" -> classworkType = ClassworkType.QUIZ;
            case "Classwork" -> classworkType = ClassworkType.CLASSWORK;
            case "Test" -> classworkType = ClassworkType.TEST;
        }

        Course course = courseRepository.findById(courseID).orElseThrow(
               () -> new ResourceNotFoundException("Course not found")
       );

        ClassWork classWork = ClassWork.builder()
                .classID(request.classRoomID())
                .course(course)
                .startTime(parseStringToLocalTime(request.startTime()))
                .endTime(parseStringToLocalTime(request.endTime()))
                .description(request.description())
                .classworkType(classworkType)
                .createdAt(new Date())
                .maxScore(request.maxScore())
                .dateToBeDone(request.dateToBeDone())
                .build();
        //saving the classwork
        ClassWork savedClassWork = classWorkRepository.save(classWork);

        //add classwork to courses classWorks
        course.getClassWorks().add(savedClassWork);


        //saving the course with classWorks added
        courseRepository.save(course);

        //Publish newClassWork Event created
        messageController.publishNewClasswork(savedClassWork);

        return simpleResponse(savedClassWork);
    }

    public ClassWorkRegistrationResponse simpleResponse(ClassWork classWork) {

        return new ClassWorkRegistrationResponse(
                classWork.getClassWorkID(),

                classWork.getClassworkType(),
                classWork.getDescription(),
                (int) classWork.getMaxScore(),
//                classWork.getClassID(),
                classWork.getCourse().getName(),

                classWork.getDateToBeDone(),
                classWork.getStartTime() ,
                classWork.getEndTime(),

                classWork.getCreatedAt()

        );

    }

    public ClassWork getClassWorkByID(int classWorkID) {
        return classWorkRepository.findById(classWorkID)
                .orElseThrow( () -> new ResourceNotFoundException("class Not found"));
    }

    public List<ClassWork> getClassWorkByCourseID (int courseID) {
        //Find the course if exists
       Course course = courseRepository.findById(courseID)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
       return  course.getClassWorks();

    }


    public List<ClassWorkRegistrationResponse> getClassWorksByClassRoomID(int classID) {
        List<ClassWork> classWorks = classWorkRepository.getClassWorkByClassID(classID);
        List<ClassWorkRegistrationResponse> classWorkRegistrationResponses = new ArrayList<>();
        for (ClassWork classWork : classWorks) {
            ClassWorkRegistrationResponse classWorkRegistrationResponse = new ClassWorkRegistrationResponse(
                    classWork.getClassWorkID(),


                    classWork.getClassworkType(),
                    classWork.getDescription(),
                    (int) classWork.getMaxScore(),
//                classWork.getClassID(),
                    classWork.getCourse().getName(),

                    classWork.getDateToBeDone(),
                    classWork.getStartTime(),
                    classWork.getEndTime(),
                    classWork.getCreatedAt()

            );
            classWorkRegistrationResponses.add(classWorkRegistrationResponse);
        }
        return  classWorkRegistrationResponses;
    }

    public static LocalTime parseStringToLocalTime(String timeString) {
        DateTimeFormatter[] formatters = {
                DateTimeFormatter.ofPattern("HH:mm:ss"),
                DateTimeFormatter.ofPattern("HH:mm"),
                DateTimeFormatter.ofPattern("h:mm a")
        };

        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalTime.parse(timeString, formatter);
            } catch (DateTimeParseException ignored) {
            }
        }

        System.err.println("Invalid time format: " + timeString);
        return null;
    }



}
