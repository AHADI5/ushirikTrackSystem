package com.ushirikeduc.schools.service;

import com.ushirikeduc.schools.interfaces.ClassRepository;
import com.ushirikeduc.schools.interfaces.TeacherRepository;
import com.ushirikeduc.schools.model.Classes;
import com.ushirikeduc.schools.model.Teacher;
import com.ushirikeduc.schools.requests.ClassRegistrationRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public record ClassesService(ClassRepository classRepository,
                             TeacherRepository teacherRepository
                             ) {


    public Classes registerClass(ClassRegistrationRequest Request) {
        Classes classe = Classes.builder()
                .name(Request.name())
                .level((long) Request.level())
                .build();
        return classRepository.save(classe);
    }

    public List<Classes> getAllClasses() {
        return  classRepository.findAll();
    }

    public Optional<Classes> getClassById(Long classID ) {
        return classRepository.findById(Math.toIntExact(classID));

    }
    //Assign the teacher to the class
    public void assignTeacherToClass(Teacher teacher) {
        Optional<Classes> classes = getClassById(teacher.getClassID());
        classes.ifPresent(
                c -> {
                    c.assignTeacher(teacher);
                    classRepository.save(c);
                }
        );

    }


}
