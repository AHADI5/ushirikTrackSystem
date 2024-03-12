package com.ushirikeduc.teacherservice.controller;

import com.ushirikeduc.teacherservice.model.Teacher;
import com.ushirikeduc.teacherservice.request.TeacherRegistrationRequest;
import com.ushirikeduc.teacherservice.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("api/v1/teacher")
public record Controller(TeacherService teacherService) {
    @PostMapping
    public Teacher registerTeacher(@RequestBody TeacherRegistrationRequest
                                            teacherRegistrationRequest) {
        return teacherService.saveTeacher(teacherRegistrationRequest);
    }
}
