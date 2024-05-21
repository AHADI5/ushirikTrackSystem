package com.ushirikeduc.courseservice.service;

import com.ushirikeduc.courseservice.dto.CourseCategoryRegisterRequest;
import com.ushirikeduc.courseservice.dto.CourseCategoryResponse;
import com.ushirikeduc.courseservice.dto.CourseInfo;
import com.ushirikeduc.courseservice.model.Course;
import com.ushirikeduc.courseservice.model.CourseCategory;
import com.ushirikeduc.courseservice.repository.CourseCategoryRepository;
import com.ushirikeduc.courseservice.repository.CourseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public record CourseCategoryService (
        CourseCategoryRepository courseCategoryRepository
) {
    public ResponseEntity<String> registerCourseCategory(CourseCategoryRegisterRequest registerRequest , long schoolID) {
        CourseCategory courseCategory = CourseCategory.builder()
                .name(registerRequest.name())
                .schoolID(schoolID)
                .description(registerRequest.Description())
                .build();
        CourseCategory savedCourseCategory = courseCategoryRepository.save(courseCategory);

        return  ResponseEntity.ok("Successfully Registered");
    }

    public List<CourseCategoryResponse> getCoursesCategoryBySchoolID(long schoolID) {
        List<CourseCategoryResponse> courseCategoryResponses = new ArrayList<>();
        List<CourseCategory> courseCategories = courseCategoryRepository.getCourseCategoriesBySchoolID(schoolID);
        for (CourseCategory courseCategory : courseCategories) {
            List<CourseInfo> coursesList = getSimpleCourseInfo(courseCategory.getCourses());
            CourseCategoryResponse courseCategoryResponse = new CourseCategoryResponse(
                    courseCategory.getName() ,courseCategory.getDescription() , coursesList, courseCategory.getCourseCategoryID()
            );
            courseCategoryResponses.add(courseCategoryResponse);
        }

        return  courseCategoryResponses;


    }

    public List<CourseInfo> getSimpleCourseInfo(List<Course> courses) {

        List<CourseInfo> courseInfoList = new ArrayList<>();

        for (Course course : courses) {
             CourseInfo courseInfo = new CourseInfo(course.getName() , course.getCourseID(),
                     course.getClassRoomID());
             courseInfoList.add(courseInfo);
        }
        return courseInfoList;


    }
}
