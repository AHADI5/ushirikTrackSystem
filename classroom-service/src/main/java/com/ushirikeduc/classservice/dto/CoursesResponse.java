package com.ushirikeduc.classservice.dto;

import java.util.List;

public record CoursesResponse(


        long classID ,


        String classRoom,

        List<SimpleCourseForm> courseFormList

) {

}
