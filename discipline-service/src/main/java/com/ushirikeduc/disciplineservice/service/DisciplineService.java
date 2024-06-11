package com.ushirikeduc.disciplineservice.service;

import Dto.StudentEvent;
import com.ushirikeduc.disciplineservice.Dto.AttendanceResponse;
import com.ushirikeduc.disciplineservice.Dto.DisciplineResponse;
import com.ushirikeduc.disciplineservice.Dto.IncidentResponse;
import com.ushirikeduc.disciplineservice.model.Attendance;
import com.ushirikeduc.disciplineservice.model.Discipline;
import com.ushirikeduc.disciplineservice.model.Incident;
import com.ushirikeduc.disciplineservice.repository.AttendanceRepository;
import com.ushirikeduc.disciplineservice.repository.DisciplineRepository;
import com.ushirikeduc.disciplineservice.repository.IncidentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public record DisciplineService(
        DisciplineRepository disciplineRepository ,
        IncidentRepository incidentRepository ,
        AttendanceRepository attendanceRepository
) {

    public void registerDiscipline(StudentEvent studentEvent) {
        Discipline discipline = Discipline.builder()
                .owner(studentEvent.getName())
                .classRoomID(studentEvent.getClassID())
                .ownerID(studentEvent.getStudentID())
                .build();

        disciplineRepository.save(discipline);

    }

    public DisciplineResponse getStudentDiscipline(int studentID) {
        Discipline discipline = disciplineRepository.getDisciplineByOwnerID(studentID);
        List<Incident> incidents = incidentRepository.getIncidentByDiscipline_OwnerID(studentID);
        List<Attendance> attendances = attendanceRepository.getAttendanceByDiscipline_OwnerID(studentID);

        //this list is going to store student's incidents
        List<IncidentResponse> incidentResponses = new ArrayList<>();

        //this list is going to store student's attendances
        List<AttendanceResponse> attendanceResponses = new ArrayList<>();

        for (Incident incident : incidents) {
            IncidentResponse incidentResponse = new IncidentResponse(
                    incident.getTitle(),
                    incident.getDescription(),
                    incident.getDate(),
                    incident.getSanction(),
                    incident.getRuleBypassed().getTitle()
            );
            incidentResponses.add(incidentResponse);

        }

        for(Attendance attendance : attendances){
            AttendanceResponse attendanceResponse = new AttendanceResponse(
                    attendance.getDate(),
                    attendance.getDiscipline().getOwner(),
                    attendance.isPresent()
            );
            attendanceResponses.add(attendanceResponse);
        }
        discipline.setAttendances(attendances);
        discipline.setIncident(incidents);

        return  new DisciplineResponse(
                discipline.getDisciplineID(),
                discipline.getOwner(),
                attendanceResponses,
                incidentResponses
        );



        //Get a list of attendances


    }
}
