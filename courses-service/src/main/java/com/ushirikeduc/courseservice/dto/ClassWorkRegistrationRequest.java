package com.ushirikeduc.courseservice.dto;

import com.ushirikeduc.courseservice.model.ClassworkType;
import com.ushirikeduc.courseservice.model.Course;

import java.util.Date;

public record ClassWorkRegistrationRequest (
        String type ,
        String description ,
        Date dateToBeDone ,
        String startTime ,
        String endTime ,
        int courseID ,
        int classRoomID,
        int maxScore
) {
}
