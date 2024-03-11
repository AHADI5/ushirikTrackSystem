package com.ushirikeduc.schools.controller;

import com.ushirikeduc.schools.requests.SchoolRegistrationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("api/v1/school")
public record SchoolController() {
    @PostMapping
    public void registerSchool(@RequestBody  SchoolRegistrationRequest request) {



    }
}
