package com.ushirikeduc.courseservice.dto;

import com.ushirikeduc.courseservice.model.ClassworkType;

import java.time.LocalTime;
import java.util.Date;

public record ClassWorkRegistrationResponse(
        int classWorkID ,

        ClassworkType type ,

        String description ,
        int maxScore,

        String  courseName ,

        Date dateTobeDone  ,

        LocalTime startTime  ,
        LocalTime endTime  ,

        Date CreatedAt

) {
}
