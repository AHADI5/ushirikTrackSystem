package com.ushirikeduc.schools.controller;

import com.ushirikeduc.schools.model.CommunicationType;
import com.ushirikeduc.schools.model.School;
import com.ushirikeduc.schools.requests.*;
import com.ushirikeduc.schools.service.*;
import lombok.extern.slf4j.Slf4j;
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
        SchoolAdminService schoolAdminService,

        ClassRoomService classRoomService ,
        CommuniqueTypeService communiqueTypeService
                             ) {
    @PostMapping("/register-school")
    public SchoolResponse registerSchool(@RequestBody  SchoolRegistrationRequest request  /*@RequestHeader String userName*/) {

        return schoolService.registerSchool(request);
    }



    //Register A list of Classes

//    @PostMapping("/classes/{schoolId}")
//    public ResponseEntity<String> addClassesToSchool(@PathVariable Integer schoolId,
//                                                     @RequestBody List<Classes> classesList) {
//
//        return classesService.addClassesToSchool(schoolId ,classesList);
//    }
    @GetMapping("/{schoolID}")
    public SchoolResponse getSchoolByID (@PathVariable int schoolID) {
        return schoolService.loadSchoolByID (schoolID);

    }


    @PostMapping("{schoolID}/newCommuniqueType")
    public ResponseEntity<CommunicationType> createNewCommuniqueType (@RequestBody CommuniqueTypeRequest communicationType, @PathVariable long schoolID) {
       return communiqueTypeService.createCommunicationType( schoolID, communicationType );
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


    @GetMapping("{schoolID}/events")
    public List<EventResponse> getAllEventsBySchoolID(@PathVariable int schoolID) {
        return  eventService.getSchoolEvents(schoolID);
    }

    @GetMapping("{schoolID}/upComing")
    public List<EventResponse> getUpComingEvents (@PathVariable int schoolID) {
        return eventService.getUpcomingEvents(schoolID);

    }


    @PostMapping("/schoolAdmin")
    public AdminResponse registerAdmin(@RequestBody RegisterAdminRequest request){
        return schoolAdminService.registerSchoolAdmin(request);

    }

    @GetMapping("/admin/schools")
    public List<SchoolResponse> getSchoolByAdminEmail(@RequestHeader String userName) {
        log.info("Admin email received is " + userName);
        return  schoolAdminService.getSchoolByAdminEmail(userName);
    }

    @GetMapping("/director/school")
    public School getSchoolByDirectorEmail (@RequestHeader String userName) {
        log.info("Director admin received in school successfully" + userName);
        return schoolService.getSchoolByDirectorEmail(userName) ;
    }

    @GetMapping("/director/schoolID")
    public Integer getSchoolIDByDirectorEmail(@RequestHeader String userName) {
        log.info("Director admin received in school successfully" + userName);
        return  schoolService.getSchoolIDByDirectorEmail (userName);
    }


    //Communique
    @GetMapping("/{schoolID}/recentCommuniques")
    public List<CommuniqueResponse> getRecentCommuniques(@PathVariable int schoolID){
        return communiqueService.getRecentCommunique(schoolID);
    }

    @GetMapping("{communiqueID}/communique")
    public  CommuniqueResponse getCommuniqueByID(@PathVariable int communiqueID){
        return  communiqueService.getCommuniqueByID(communiqueID) ;
    }

    // Update and Delete a communique
    @DeleteMapping("{communiqueID}/delete-communique")
    public ResponseEntity<String> deleteCommunique(@PathVariable int communiqueID){
        return communiqueService.deleteCommunique(communiqueID);
    }
    @PutMapping("{communiqueID}/update-communique")
    public ResponseEntity<String> updateCommunique(@PathVariable int communiqueID ,
                                                   @RequestBody CommuniqueRegisterRequest request ) {
        return communiqueService.updateCommunique(communiqueID, request);
    }

    //Events
    @PostMapping ("{schoolID}/getEventByStartingDate")
    public EventResponse getEventByStartingDate (@PathVariable int schoolID ,
                                                  @RequestBody EventDateRequest date){
        return  eventService.getEventByStartingDate(schoolID , date.date());
    }

    @PostMapping ("{schoolID}/getListEventByStartingDate")
    public List<EventResponse> getListEventByStartingDate (@PathVariable int schoolID ,
                                                 @RequestBody EventDateRequest date){
        return  eventService.getEventListByStartingDate(schoolID , date.date());
    }
}

