package com.ushirikeduc.courseservice.dto;

import com.ushirikeduc.courseservice.model.HomeWorkQuestion;


import java.util.List;

public record HomeworkRegistrationRequest (
        String title ,
        String Description ,
        int classRoomID ,
        List<QuestionRegistrationRequest> questions

) {
}
