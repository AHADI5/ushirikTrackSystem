package com.ushirikeduc.schools.service;

import com.ushirikeduc.schools.interfaces.SchoolInterface;
import com.ushirikeduc.schools.model.School;
import com.ushirikeduc.schools.requests.SchoolRegistrationRequest;
import org.springframework.stereotype.Service;

@Service
public record SchoolService(SchoolInterface schoolInterface) {
    public void registerSchool(SchoolRegistrationRequest request) {
        School school = School.builder()
                .schoolName(request.schoolName())
                .schoolPostalBox(request.schoolName())
                .address(request.address())
                .director(request.director())
                .build();

    }
}
