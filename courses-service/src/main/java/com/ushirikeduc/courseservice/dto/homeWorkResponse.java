package com.ushirikeduc.courseservice.dto;

import java.util.Date;
import java.util.List;

public record homeWorkResponse  (
        List<String> courseInvolved,
        String homeWorkTitle ,
        String description ,
        Date creationDate ,
        List<QuestionResponse> questions
){
}
