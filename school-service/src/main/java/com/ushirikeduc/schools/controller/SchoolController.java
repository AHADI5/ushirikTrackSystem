package com.ushirikeduc.schools.controller;

import com.ushirikeduc.schools.model.Classes;
import com.ushirikeduc.schools.model.Director;
import com.ushirikeduc.schools.model.EnrolledStudent;
import com.ushirikeduc.schools.model.School;
import com.ushirikeduc.schools.requests.ClassRegistrationRequest;
import com.ushirikeduc.schools.requests.ClassStudentsResponse;
import com.ushirikeduc.schools.requests.SchoolRegistrationRequest;
import com.ushirikeduc.schools.service.ClassesService;
import com.ushirikeduc.schools.service.SchoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("api/v1/school")
public record SchoolController(SchoolService schoolService,
                               ClassesService classesService ) {
    @PostMapping("/register-school")
    public School registerSchool(@RequestBody  SchoolRegistrationRequest request) {
        return schoolService.registerSchool(request);
    }

    @PostMapping("/register-class")
    public Classes registerClass(@RequestBody ClassRegistrationRequest request)  {
        return  classesService.registerClass(request);
    }

    //Register A list of Classes

    @PostMapping("/classes/{schoolId}")
    public ResponseEntity<String> addClassesToSchool(@PathVariable Integer schoolId,
                                                     @RequestBody List<Classes> classesList) {

        return classesService.addClassesToSchool(schoolId ,classesList);
    }



    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSchoolWithDetails(@PathVariable("id") Integer schoolId) {
        Optional<School> school = schoolService.getSchool(schoolId);
        return ResponseEntity.ok(school);
    }

    @GetMapping(value = "/director/{schoolID}")
    public Director getSchoolDirector(@PathVariable("schoolID") Integer schoolID){
        return schoolService.getDirector(schoolID);
    }
    @GetMapping( value = "/classes")
    // todo : Getting classes per school instead
    public List<Classes> getClasses() {
        return classesService.getAllClasses();
    }
    @GetMapping("/classes/{schoolID}")
    public ResponseEntity<List<Classes>> getSchoolClasses(@PathVariable Integer schoolID){
      return  classesService.getClassesBySchoolId(schoolID);
    }

    @GetMapping ("/{classId}/students")
    public ResponseEntity<List<ClassStudentsResponse>> getStudentsInClass(@PathVariable
                                                 Long classId){
        return  classesService.getStudentInClass(classId);
    }


}

