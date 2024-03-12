package com.ushirikeduc.schools.service;

import com.ushirikeduc.schools.interfaces.ClassRepository;
import com.ushirikeduc.schools.model.Classes;
import com.ushirikeduc.schools.requests.ClassRegistrationRequest;
import org.springframework.stereotype.Service;

@Service
public record ClassesService(ClassRepository classRepository) {
    public Classes registerClass(ClassRegistrationRequest Request) {
        Classes classe = Classes.builder()
                .classesID(Request.teacherID())
                .name(Request.name())
                .level(Long.valueOf(Request.Level()))
                .build();
        return classRepository.save(classe);
    }
}
