package com.ushirikeduc.schools.controller;

import com.ushirikeduc.schools.model.Director;
import com.ushirikeduc.schools.model.School;
import com.ushirikeduc.schools.requests.SchoolRegistrationRequest;
import com.ushirikeduc.schools.service.SchoolService;
import com.ushirikeduc.schools.service.SchoolWithDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("api/v1/school")
public record SchoolController(SchoolService service) {
    @PostMapping
    public School registerSchool(@RequestBody  SchoolRegistrationRequest request) {
        return service.registerSchool(request);
    }
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSchoolWithDetails(@PathVariable("id") Integer schoolId) {
        Optional<School> school = service.getSchool(schoolId);
        return ResponseEntity.ok(school);
    }
    @GetMapping(value = "/director/{schoolID}")
    public Director getSchoolDirector(@PathVariable("schoolID") Integer schoolID){
        return service.getDirector(schoolID);
    }


}
