package com.ushirikeduc.courseservice.dto;

import com.ushirikeduc.courseservice.model.ClassworkType;

import java.util.Date;

public record ClassWorkRegistrationResponse(
        int classWorkID ,

        ClassworkType type ,

        String description ,
        int maxScore,

        String  courseName ,

        Date dateTobeDone  ,

        Date CreatedAt

) {
}
