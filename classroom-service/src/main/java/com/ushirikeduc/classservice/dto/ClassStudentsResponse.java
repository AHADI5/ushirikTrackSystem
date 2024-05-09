package com.ushirikeduc.classservice.dto;

import com.ushirikeduc.classservice.model.Teacher;

import java.util.List;

public record ClassStudentsResponse(
//        Teacher teacher,
        String className,
        int level,
        String classRoomOptionName ,
        List<EnrolledStudentResponse> students

) {
}
