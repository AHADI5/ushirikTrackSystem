package com.ushirikeduc.classservice.dto;

import java.util.List;

public record SimpleTeacherForm (
        String name  ,
        String email ,
        boolean isTitular ,
        String schoolType ,

        int schoolID  ,
        ClassInfoResponse classRoom  ,
        List <SimpleCourseForm> courses
){
}
