package com.ushirikeduc.courseservice.dto;

import com.ushirikeduc.courseservice.model.HomeWorkQuestion;


import java.util.Date;
import java.util.List;

public record HomeworkRegistrationRequest (
        String type ,
        String description ,
        Double maxScore ,
        int classRoomID ,
        Date dueDate,
        List<QuestionRegistrationRequest> questions
) {
}
