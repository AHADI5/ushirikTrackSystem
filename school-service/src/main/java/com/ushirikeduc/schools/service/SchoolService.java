package com.ushirikeduc.schools.service;

import com.ushirikeduc.schools.controller.SchoolRegistrationRequest;
import com.ushirikeduc.schools.model.School;

public record SchoolService() {
    public  void registerSchool(SchoolRegistrationRequest request) {
        //Converting the request into a School
        School school = School.builder()
                .name(request.name())
                .email(request.email())
                .postalBox(request.postalBox())
                .build();

    }
}
