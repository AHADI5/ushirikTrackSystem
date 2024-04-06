package com.ushirikeduc.schools.controller;

import com.ushirikeduc.schools.model.School;
import com.ushirikeduc.schools.requests.*;
import com.ushirikeduc.schools.service.CommuniqueService;
import com.ushirikeduc.schools.service.EventService;
import com.ushirikeduc.schools.service.RuleService;
import com.ushirikeduc.schools.service.SchoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/v1/school")
public record SchoolController(
        SchoolService schoolService,
        RuleService ruleService ,
        EventService eventService,
        CommuniqueService communiqueService
                             ) {
    @PostMapping("/register-school")
    public School registerSchool(@RequestBody  SchoolRegistrationRequest request) {
        return schoolService.registerSchool(request);
    }



    //Register A list of Classes

//    @PostMapping("/classes/{schoolId}")
//    public ResponseEntity<String> addClassesToSchool(@PathVariable Integer schoolId,
//                                                     @RequestBody List<Classes> classesList) {
//
//        return classesService.addClassesToSchool(schoolId ,classesList);
//    }



    @GetMapping(value = "/{schoolID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSchoolWithDetails(@PathVariable("schoolID") Integer schoolId) {
        School school = schoolService.getSchool(schoolId);
        return ResponseEntity.ok(school);
    }

    @PostMapping("{schoolID}/newRule")
    public List<RuleResponse> registerRule (@PathVariable int schoolID ,
                                            @RequestBody List<RuleRegistrationRequest> request){
        return  ruleService.registerRule (schoolID , request) ;
    }

    @PostMapping("{schoolID}/newCommunique")
    public CommuniqueResponse registerCommunique(@PathVariable int schoolID ,
                                                 @RequestBody CommuniqueRegisterRequest request) {
        return  communiqueService.registerCommunique(schoolID , request);
    }

    @PostMapping("{schoolID}/newEvent")
    public EventResponse registerEvent (@PathVariable int schoolID,
                                        @RequestBody EventRegisterRequest request) {
        return  eventService.registerNewEvent(schoolID , request);

    }

    @GetMapping("{schoolID}/communications")
    public List<CommuniqueResponse> getCommuniqueBySchoolID(@PathVariable  int schoolID) {
        return  communiqueService.getAllCommuniqueBySchoolID(schoolID);
    }
    @GetMapping("{schoolID}/rules")
    public List<RuleResponse> getRulesBySchoolID(@PathVariable int schoolID){
        return ruleService.getRulesBySchoolID(schoolID);
    }
    @GetMapping("{schoolID}/events")
    public List<EventResponse> getAllEventsBySchoolID(@PathVariable int schoolID) {
        return  eventService.getSchoolEvents(schoolID);
    }

    @GetMapping("{schoolID}/upComing")
    public List<EventResponse> getUpComingEvents (@PathVariable int schoolID) {
        return eventService.getUpcomingEvents(schoolID);

    }
}

