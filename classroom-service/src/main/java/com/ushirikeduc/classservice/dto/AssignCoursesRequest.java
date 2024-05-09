package com.ushirikeduc.classservice.dto;

import java.util.List;

public record AssignCoursesRequest(
        long teacherID ,
        List<Integer> courseIDs
) {
}
