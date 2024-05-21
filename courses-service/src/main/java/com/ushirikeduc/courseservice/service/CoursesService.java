package com.ushirikeduc.courseservice.service;


import com.ushirikeduc.courseservice.controller.MessageController;
import com.ushirikeduc.courseservice.dto.CourseGeneralInfo;
import com.ushirikeduc.courseservice.dto.CourseInfo;
import com.ushirikeduc.courseservice.dto.CourseRegistrationRequest;
import com.ushirikeduc.courseservice.model.Course;
import com.ushirikeduc.courseservice.model.CourseCategory;
import com.ushirikeduc.courseservice.model.RequiredTool;
import com.ushirikeduc.courseservice.model.Requirement;
import com.ushirikeduc.courseservice.repository.CourseCategoryRepository;
import com.ushirikeduc.courseservice.repository.CourseRepository;
import com.ushirikeduc.courseservice.repository.RequiredToolRepository;
import com.ushirikeduc.courseservice.repository.RequirementRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public record CoursesService(
        CourseRepository courseRepository,
        MessageController messageController ,
        CourseCategoryRepository courseCategoryRepository ,
        RequiredToolRepository requiredToolRepository ,

        RequirementRepository requirementRepository

) {

    public ResponseEntity<String> registerCourse( CourseRegistrationRequest courseRegistrationRequest  ) {
        //Saving the course
        CourseCategory courseCategory = courseCategoryRepository.findById(courseRegistrationRequest.courseCategory())
                .orElseThrow(() -> new ResourceNotFoundException("No category found"));

        //Saving required tools
        List< RequiredTool> requiredTools = new ArrayList<>();
        for (RequiredTool requiredTool : courseRegistrationRequest.tools()){
            RequiredTool requiredToolNet = RequiredTool
                    .builder()
                    .name(requiredTool.getName())
                    .build();
            requiredTools.add(requiredToolNet);
        }
        List<RequiredTool> requiredToolSaved = requiredToolRepository.saveAll(requiredTools);

        //Saving requirement
        List<Requirement> requirements = new ArrayList<>();

        for (Requirement requirement : courseRegistrationRequest.requirements()) {
            Requirement courseRequirement  = Requirement
                    .builder()
                    .name(requirement.getName())
                    .Description(requirement.getDescription())

                    .build();
            requirements.add(courseRequirement);
        }

        List<Requirement> savedRequirement = requirementRepository.saveAll(requirements);


        Course course = Course.builder()
                .name(courseRegistrationRequest.name())
                .description(courseRegistrationRequest.description())
                .courseCategory(courseCategory)
                .credits((int) courseRegistrationRequest.Credits())
                .tools(requiredToolSaved)
                .classRoomID(courseRegistrationRequest.classRoomID())
                .requirements(savedRequirement)
                .build();
        Course newCourse = courseRepository.save(course);
        messageController.publishCourse(newCourse);

        return ResponseEntity.ok("Success !!");
    }

//    public ResponseEntity<String> updateCourseInfo (int CourseID  ,CourseRegistrationRequest courseRegistrationRequest) {
//        Course course = courseRepository.findById(CourseID)
//                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
//        //updating course informations
//
//
//    }


    public CourseInfo getCourseByID(int courseID) {
        Course course =  courseRepository.findById(courseID).orElseThrow(
                () -> new ResourceNotFoundException("not found")
        );
        return new CourseInfo(
                course.getName() ,
                course.getCourseID(),
                course.getClassRoomID()
        );
    }

    public List<CourseGeneralInfo> getCoursesByClassID(int classID) {
        List<CourseGeneralInfo> courseGeneralInfos = new ArrayList<>();
        List<Course> courses = courseRepository.getCourseByClassRoomID(classID);

        for (Course course : courses){
            CourseGeneralInfo  courseGeneralInfo = new CourseGeneralInfo(
                    course.getName(),
                    course.getDescription(),
                    course.getCourseCategory().getName() ,
                    course.getCourseID(),
                    courses.getFirst().getCredits(),
                    course.getTools(),
                    course.getRequirements(),
                    course.getClassWorks().size()
            );
            courseGeneralInfos.add(courseGeneralInfo);
        }
        return  courseGeneralInfos;

    }
}
