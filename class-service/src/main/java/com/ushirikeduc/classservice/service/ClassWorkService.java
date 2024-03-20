package com.ushirikeduc.classservice.service;

import com.ushirikeduc.classservice.dto.ClassWorkRegistrationRequest;
import com.ushirikeduc.classservice.model.ClassRoom;
import com.ushirikeduc.classservice.model.Course;
import com.ushirikeduc.classservice.model.Student;
import com.ushirikeduc.classservice.repository.ClassRoomRepository;
import com.ushirikeduc.classservice.repository.ClassWorkRepository;
import com.ushirikeduc.classservice.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public record ClassWorkService(
        ClassRoomRepository classRoomRepository,
        CoursesService service,
        CourseRepository courseRepository,
        ClassWorkRepository classWorkRepository

) {



    public ClassWork registerClassWork(ClassWorkRegistrationRequest request,
                                       int courseID) {
        //todo : finding the course
        //todo : set the course's classwork to the new classwork
        //todo : get all Student from the class


        Course course = courseRepository.findById(courseID)
                .orElseThrow(() -> new ResourceNotFoundException("Course Not found"));

        //find the classroom from the course
        ClassRoom classRoom = course.getClassRoom();
        List<Student> students = classRoom.getStudents();

        ClassWork classWork = ClassWork.builder()
                .course(course)
                .type(request.type())
                .description(request.Description())

                .credits(request.credits())
                .build();
        classWorkRepository.save(classWork);

        return  classWork ;

    }

    public List<Student> getStudentsParticipated(int classWorkID) {
        ClassWork classWork = classWorkRepository.findById(classWorkID)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
        return classWork.getStudents();

    }

}
