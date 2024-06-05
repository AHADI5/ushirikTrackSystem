package com.ushirikeduc.courseservice.dto;

import com.ushirikeduc.courseservice.model.ClassworkType;
import com.ushirikeduc.courseservice.model.Course;

import java.util.Date;

public record ClassWorkRegistrationRequest (
        String type ,

        String description ,

        Date dateTobeDone  ,
        int courseID ,
        int classID,
        int maxScore
) {
}
