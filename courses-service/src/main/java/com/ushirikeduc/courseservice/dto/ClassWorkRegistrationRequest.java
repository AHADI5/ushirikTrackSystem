package com.ushirikeduc.courseservice.dto;

import com.ushirikeduc.courseservice.model.Course;

public record ClassWorkRegistrationRequest (
       String name ,
       String description ,

       String classWorkType ,
       int classID,
       int credits
) {
}
