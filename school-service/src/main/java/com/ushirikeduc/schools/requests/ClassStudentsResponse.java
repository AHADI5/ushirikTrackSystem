package com.ushirikeduc.schools.requests;

import com.ushirikeduc.schools.model.Teacher;

import java.util.List;

public record ClassStudentsResponse(

        Teacher teacher,
        String className,
        int level,
        List<EnrolledStudentResponse> students



) {

}
