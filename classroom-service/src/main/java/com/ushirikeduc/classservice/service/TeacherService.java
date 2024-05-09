package com.ushirikeduc.classservice.service;

import com.ushirikeduc.classservice.model.Course;
import com.ushirikeduc.classservice.model.Teacher;
import com.ushirikeduc.classservice.repository.ClassRoomRepository;
import com.ushirikeduc.classservice.repository.CourseRepository;
import com.ushirikeduc.classservice.repository.TeacherRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
@Service
@Slf4j
public record TeacherService (
        TeacherRepository teacherRepository ,
        CourseRepository courseRepository ,
        ClassRoomRepository classRoomRepository
) {
    public Teacher getTeacherByID(long teacherID) {
        return teacherRepository.findById(teacherID)
                .orElseThrow(() -> new ResourceNotFoundException("teacher Not found"));
    }
}
