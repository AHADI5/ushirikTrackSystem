package com.ushirikeduc.teacherservice.controller;

import com.ushirikeduc.teacherservice.model.Teacher;
import com.ushirikeduc.teacherservice.request.TeacherRegistrationRequest;
import com.ushirikeduc.teacherservice.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("api/v1/teacher")
public record Controller(TeacherService teacherService) {
    @PostMapping("/new-teacher")
    public Teacher registerTeacher(@RequestBody TeacherRegistrationRequest
                                            teacherRegistrationRequest) {
        return teacherService.saveTeacher(teacherRegistrationRequest);
    }

    @PutMapping("/{userEMail}/modifyTeacherInfo")
    public ResponseEntity<String> modifyTeacher (@PathVariable String userEMail ,
                                                 @RequestBody TeacherRegistrationRequest request) {

        return teacherService.modifyTeacherInformation(userEMail , request);

    }
}
