package com.ushirikeduc.schools.controller;

import com.ushirikeduc.schools.service.SchoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("api/v1/schools")

public class SchoolController {
    //Register a new school
    // Taking The customer from the request and register
    SchoolService schoolService;
    @PostMapping
    public void registerSchool(@RequestBody SchoolRegistrationRequest schoolRegistrationRequest) {
        log.info("New School registration {}", schoolRegistrationRequest);
        schoolService.registerSchool(schoolRegistrationRequest);
    }

}
