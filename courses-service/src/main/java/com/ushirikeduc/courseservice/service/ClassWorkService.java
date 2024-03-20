package com.ushirikeduc.courseservice.service;

import com.ushirikeduc.courseservice.dto.ClassWorkRegistrationRequest;
import com.ushirikeduc.courseservice.dto.ClassWorkRegistrationResponse;
import com.ushirikeduc.courseservice.model.ClassWork;
import com.ushirikeduc.courseservice.model.Course;
import com.ushirikeduc.courseservice.repository.ClassWorkRepository;
import com.ushirikeduc.courseservice.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public record ClassWorkService (
        CourseRepository courseRepository,
        ClassWorkRepository classWorkRepository
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
        ClassWork savedClassWork = classWorkRepository.save(classWork);
        return simpleResponse(savedClassWork);


    }

    public ClassWorkRegistrationResponse simpleResponse(ClassWork classWork) {

        return new ClassWorkRegistrationResponse(
                classWork.getName(),
                classWork.getDescription(),
                classWork.getCourse().getName(),
                classWork.getCredits()
        );

    }
}
