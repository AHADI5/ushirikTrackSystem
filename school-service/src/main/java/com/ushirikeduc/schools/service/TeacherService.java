package com.ushirikeduc.schools.service;

import Dto.TeacherEvent;
import com.ushirikeduc.schools.interfaces.TeacherRepository;
import com.ushirikeduc.schools.model.Teacher;
import org.springframework.stereotype.Service;

@Service
public record TeacherService(TeacherRepository teacherRepository) {
    public void registerTeacher (TeacherEvent teacherEvent) {
        Teacher teacher = Teacher.builder()
                .name(teacherEvent.getName())
                .teacherID(teacherEvent.getTeacherID())
                .classID(teacherEvent.getClassID())
                .build();
        teacherRepository.save(teacher);
    }








}
