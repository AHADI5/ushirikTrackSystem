package com.ushirikeduc.classservice.dto;

import com.ushirikeduc.classservice.model.Teacher;

import java.util.List;

public record ClassStudentsResponse(
        Teacher teacher,
        String className,
        int level,
        List<EnrolledStudentResponse> students

) {
}
