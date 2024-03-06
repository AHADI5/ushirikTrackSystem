package com.ushirikeduc.schools.service;

import com.ushirikeduc.schools.controller.SchoolRegistrationRequest;
import com.ushirikeduc.schools.interfaces.SchoolInterface;
import com.ushirikeduc.schools.model.School;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public record SchoolService(SchoolInterface schoolInterface) {
    public  void registerSchool(SchoolRegistrationRequest request) {
        //Converting the request into a School
        School school = School.builder()
                .name(request.name())
                .email(request.email())
                .postalBox(request.postalBox())
                .address(request.address())
                .build();
        schoolInterface.save(school);
    }


    public School getSchool(int id) {
        Optional<School> schoolOptional = schoolInterface.findById(id);
        return schoolOptional.orElse(null); // Si l'école n'existe pas, retourne null
    }




}
