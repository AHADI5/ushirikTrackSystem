package com.ushirikeduc.courseservice.service;

import Dto.ClassWorkEvent;
import com.ushirikeduc.courseservice.dto.ClassWorkRegistrationRequest;
import com.ushirikeduc.courseservice.dto.ClassWorkRegistrationResponse;
import com.ushirikeduc.courseservice.kafka.ClassWorkProducer;
import com.ushirikeduc.courseservice.model.ClassWork;
import com.ushirikeduc.courseservice.model.Course;
import com.ushirikeduc.courseservice.repository.ClassWorkRepository;
import com.ushirikeduc.courseservice.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public record ClassWorkService (
        CourseRepository courseRepository,
        ClassWorkRepository classWorkRepository,
        ClassWorkProducer classWorkProducer
) {

    public ClassWorkRegistrationResponse registerNewClassWork(int courseID, ClassWorkRegistrationRequest request) {
       Course course = courseRepository.findById(courseID).orElseThrow(
               () -> new ResourceNotFoundException("Course not found")
       );

        ClassWork classWork = ClassWork.builder()
                .classID(request.classID())
                .course(course)
                .name(request.name())
                .description(request.description())
                .credits(request.credits())
                .build();
        //saving the classwork
        ClassWork savedClassWork = classWorkRepository.save(classWork);

        //add classwork to courses classWorks
        course.getClassWorks().add(savedClassWork);

        //Publishing classWork to subscribers
        ClassWorkEvent classWorkEvent =  createClassWorkEvent(savedClassWork);
        classWorkProducer.sendMessage(classWorkEvent);
        //saving the course with classWorks added
        courseRepository.save(course);
        return simpleResponse(savedClassWork);


    }

    private ClassWorkEvent createClassWorkEvent(ClassWork classWork) {
        ClassWorkEvent  classWorkEvent = new ClassWorkEvent();
        classWorkEvent.setCourseID(classWork.getCourse().getCourseID());
        classWorkEvent.setTitle(classWork.getName());

        return classWorkEvent;

    }

    public ClassWorkRegistrationResponse simpleResponse(ClassWork classWork) {

        return new ClassWorkRegistrationResponse(
                classWork.getName(),
                classWork.getDescription(),
                classWork.getCourse().getName(),
                classWork.getCredits()
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
}
