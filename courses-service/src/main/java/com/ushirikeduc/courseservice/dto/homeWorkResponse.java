package com.ushirikeduc.courseservice.dto;

import java.util.List;

public record homeWorkResponse  (
        List<String> courseInvolved,
        String homeWorkTitle ,
        String description ,
        List<QuestionResponse> questions
){
}
