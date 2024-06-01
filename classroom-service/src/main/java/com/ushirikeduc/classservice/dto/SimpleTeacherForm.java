package com.ushirikeduc.classservice.dto;

import java.util.List;

public record SimpleTeacherForm (
        String name  ,
        String email ,
        boolean isTitular ,
        String schoolType ,
        ClassInfoResponse classRoom  ,
        List <SimpleCourseForm> courses
){
}
