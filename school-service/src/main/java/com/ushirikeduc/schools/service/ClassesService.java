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

    public void assignTeacherToClass(TeacherRequest teacherRequest) {
        AssignedTeacher assignedTeacher = AssignedTeacher.builder()
                .name(teacherRequest.name())

                .classID(teacherRequest.classID())
                .TeacherID(teacherRequest.TeacherID())
                .build();
        AssignedTeacher teacher =teacherRepository.save(assignedTeacher);

        // Assigning Class to teacher
        Optional<Classes> selectedClass = this.getClassById(teacher.getClassID());
        selectedClass.get().setTeacher(assignedTeacher);
    }
    public Classes registerClass(ClassRegistrationRequest Request) {
        Classes classe = Classes.builder()
                .name(Request.name())
                .level((long) Request.level())
                .build();
        return classRepository.save(classe);
    }

    public Optional<Classes> getClassById(Integer classID ) {
        return classRepository.findById(classID);

    }


}
