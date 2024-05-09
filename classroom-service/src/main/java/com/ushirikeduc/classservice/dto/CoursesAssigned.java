package com.ushirikeduc.classservice.dto;

import com.ushirikeduc.classservice.model.Course;
import com.ushirikeduc.classservice.model.Teacher;

import java.util.List;

public record CoursesAssigned(
        String teacherName,
        List<Course> courseList
) {
}
