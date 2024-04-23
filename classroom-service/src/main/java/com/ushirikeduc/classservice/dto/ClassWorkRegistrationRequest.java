package com.ushirikeduc.classservice.dto;

import com.ushirikeduc.classservice.model.Course;

public record ClassWorkRegistrationRequest(
        String name ,
        String Description ,
        String type ,
        int credits,
        Course course

) {
}
