package com.ushirikeduc.schools.service;

import com.ushirikeduc.schools.interfaces.ClassRepository;
import com.ushirikeduc.schools.interfaces.TeacherRepository;
import com.ushirikeduc.schools.model.Classes;
import com.ushirikeduc.schools.requests.ClassRegistrationRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public record ClassesService(ClassRepository classRepository,
                             TeacherRepository teacherRepository) {


    public Classes registerClass(ClassRegistrationRequest Request) {
        Classes classe = Classes.builder()
                .name(Request.name())
                .level((long) Request.level())
                .build();
        return classRepository.save(classe);
    }

    public Optional<Classes> getClassById(Long classID ) {
        return classRepository.findById(Math.toIntExact(classID));

    }


}
