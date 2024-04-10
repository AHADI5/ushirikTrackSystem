package com.ushirikeduc.disciplineservice.controller;

import com.ushirikeduc.disciplineservice.Dto.*;
import com.ushirikeduc.disciplineservice.service.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public record DisciplineController(

        AttendanceService attendanceService ,

        IncidentService incidentService ,
        RuleService ruleService

) {
    @PostMapping("api/v1/{studentID}/attendance")
    public AttendanceResponse recordAttendance(@PathVariable int studentID ,
                                               @RequestBody AttendanceRegisterRequest request) {
        return attendanceService.recordAttendance(studentID, request) ;

    }
    @PostMapping("api/v1/newRules")
    public List<RuleResponse> recordNewRule(
                                      @RequestBody List<RuleRegisterRequest> requests) {
        return  ruleService.registerRule(requests);

    }

    @PostMapping("api/v1/{studentID}/incident")
    public IncidentResponse recordIncident(@PathVariable int studentID ,
                                           @RequestBody IncidentRegisterRequest request) {
        return incidentService.registerIncident(studentID , request);
    }

}
