package com.ushirikeduc.disciplineservice.controller;

import com.ushirikeduc.disciplineservice.Dto.*;
import com.ushirikeduc.disciplineservice.service.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/discipline")
public record DisciplineController(

        AttendanceService attendanceService ,
        IncidentService incidentService ,
        RuleService ruleService

) {
    @PostMapping("{classRoomID}/attendance")
    public List<AttendanceResponse> recordAttendance(@RequestBody AttendanceRegisterRequest requests, @PathVariable int  classRoomID) {
        return attendanceService.recordAttendance( requests , classRoomID) ;

    }
    @PostMapping("/newRules")
    public List<RuleResponse> recordNewRule(
                                      @RequestBody List<RuleRegisterRequest> requests) {
        return  ruleService.registerRule(requests);

    }

    @PostMapping("/{studentID}/incident")
    public IncidentResponse recordIncident(@PathVariable int studentID ,
                                           @RequestBody IncidentRegisterRequest request) {
        return incidentService.registerIncident(studentID , request);
    }
    
    @PostMapping("/attendancesByClassRoom")
    public AttendanceListInClassRoom getAttendanceByDateInClassRoom( @RequestBody AttendanceByDateRequest attendance){
        return attendanceService.getAttendancesByDateInClassRoom(attendance);
    }

    @GetMapping("{studentID}/getIncidentByOwnerID")
    public List<IncidentResponse> incidentListByOwnerID(@PathVariable int studentID){
        return incidentService.getIncidentsByDiscipline(studentID).incidents();
    }

    @GetMapping("{classRoomID}/getIncidentInClassRoom")
    public  List<OwnerIncidentsList> getIncidentInClassRoom  (@PathVariable int classRoomID) {
        return incidentService.getIncidentsByClassRoomId(classRoomID);

    }

    @GetMapping("{schoolID}/getRulesBySchoolID")
    public  List<RuleResponse> getRulesBySchoolID (@PathVariable int schoolID) {
        return  ruleService.getRulesBySchoolID (schoolID) ;
    }

}
