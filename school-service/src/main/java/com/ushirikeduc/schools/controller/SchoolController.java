package com.ushirikeduc.schools.controller;

import com.ushirikeduc.schools.model.School;
import com.ushirikeduc.schools.requests.*;
import com.ushirikeduc.schools.service.CommuniqueService;
import com.ushirikeduc.schools.service.EventService;
import com.ushirikeduc.schools.service.SchoolAdminService;
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

        EventService eventService,
        CommuniqueService communiqueService ,
        SchoolAdminService schoolAdminService
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
    @CrossOrigin("*")
    @GetMapping("{schoolID}/events")
    public List<EventResponse> getAllEventsBySchoolID(@PathVariable int schoolID) {
        return  eventService.getSchoolEvents(schoolID);
    }

    @GetMapping("{schoolID}/upComing")
    public List<EventResponse> getUpComingEvents (@PathVariable int schoolID) {
        return eventService.getUpcomingEvents(schoolID);

    }
    @CrossOrigin(origins = "*", allowedHeaders = "*",
            methods = {RequestMethod.POST, RequestMethod.OPTIONS})
    @PostMapping("/schoolAdmin")
    public AdminResponse registerAdmin(@RequestBody RegisterAdminRequest request){
        return schoolAdminService.registerSchoolAdmin(request);

    }
}

