package com.ushirikeduc.schools.controller;

import com.ushirikeduc.schools.model.School;
import com.ushirikeduc.schools.service.SchoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/schools")

public record SchoolController(SchoolService schoolService) {
    //Register a new school
    // Taking The customer from the request and register

    @PostMapping
    public void registerSchool(@RequestBody SchoolRegistrationRequest schoolRegistrationRequest) {
        log.info("New School registration {}", schoolRegistrationRequest);
        schoolService.registerSchool(schoolRegistrationRequest);
    }
    @GetMapping(path = "{idSchool}")
    public School getSchool(@PathVariable("idSchool") Integer idSchool) {

        return schoolService.getSchool(idSchool);


    }


}
