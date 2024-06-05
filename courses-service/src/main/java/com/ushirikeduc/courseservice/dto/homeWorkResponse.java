package com.ushirikeduc.courseservice.dto;

import com.ushirikeduc.courseservice.model.ClassworkType;

import java.util.Date;
import java.util.List;

public record homeWorkResponse  (
        List<String> courseInvolved,

        String type  ,
        String homeWorkTitle ,
        String description ,
        Date creationDate ,
        List<QuestionResponse> questions
){
}
