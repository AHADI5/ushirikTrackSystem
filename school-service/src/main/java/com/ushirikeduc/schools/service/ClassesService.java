package com.ushirikeduc.schools.service;

import com.ushirikeduc.schools.interfaces.ClassRepository;
import com.ushirikeduc.schools.interfaces.TeacherRepository;
import com.ushirikeduc.schools.model.AssignedTeacher;
import com.ushirikeduc.schools.model.Classes;
import com.ushirikeduc.schools.requests.ClassRegistrationRequest;
import com.ushirikeduc.schools.requests.TeacherRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public record ClassesService(ClassRepository classRepository,
                             TeacherRepository teacherRepository) {

    public void assignTeacherToClass(Integer classID,
                                     TeacherRequest teacherRequest) {
        AssignedTeacher assignedTeacher = AssignedTeacher.builder()
                .name(teacherRequest.name())
                .TeacherID(teacherRequest.TeacherID())
                .build();
        teacherRepository.save(assignedTeacher);

        // Assigning Class to teacher
        Optional<Classes> selectedClass = this.getClassById(classID);
        selectedClass.get().setTeacher(assignedTeacher);
    }
    public Classes registerClass(ClassRegistrationRequest Request) {
        Classes classe = Classes.builder()
                .classesID(Request.teacherID())
                .name(Request.name())
                .level(Long.valueOf(Request.Level()))
                .build();
        return classRepository.save(classe);
    }

    public Optional<Classes> getClassById(Integer classID ) {
        return classRepository.findById(classID);

    }


}
