package com.ushirikeduc.disciplineservice.service;

import Dto.DisciplineEvent;
import com.ushirikeduc.disciplineservice.Dto.IncidentRegisterRequest;
import com.ushirikeduc.disciplineservice.Dto.IncidentResponse;
import com.ushirikeduc.disciplineservice.controller.MessageController;
import com.ushirikeduc.disciplineservice.model.*;
import com.ushirikeduc.disciplineservice.repository.DisciplineRepository;
import com.ushirikeduc.disciplineservice.repository.IncidentRepository;
import com.ushirikeduc.disciplineservice.repository.RuleRepository;
import com.ushirikeduc.disciplineservice.repository.ViolationRepository;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public record IncidentService(
        DisciplineRepository disciplineRepository ,
        IncidentRepository incidentRepository,
        RuleRepository ruleRepository,
        ViolationRepository violationRepository,
        MessageController messageController
) {
    public IncidentResponse registerIncident(long studentID ,
                                             IncidentRegisterRequest request){

        Rule byPassedRule = ruleRepository.findById(request.byPassedRule())
                .orElseThrow(() -> new ResourceNotFoundException("Rule not found"));
        Incident existingIncident = incidentRepository.getIncidentByDiscipline_OwnerIDAndRuleBypassed(studentID ,byPassedRule);
        Discipline discipline = disciplineRepository.getDisciplineByOwnerID(studentID);

        Incident incident = Incident.builder()
                .date(new Date())
                .title(request.title())
                .ruleBypassed(byPassedRule)
                .description(request.Description())
                .place(request.place())
                .discipline(discipline)
                .build();
        int occurrence;
        if (existingIncident != null) {
            occurrence = existingIncident.getOccurrenceNumber() + 1;
            incident.setOccurrenceNumber(occurrence);
            ViolationType violationType = violationRepository.findViolationTypeByOccurrenceAndRule(incident.getOccurrenceNumber(), byPassedRule);
            incident.setSanction(violationType.getSanction());


        } else {
            occurrence = incident.getOccurrenceNumber() + 1;
            incident.setOccurrenceNumber(occurrence);

            ViolationType violationType = violationRepository.findViolationTypeByOccurrenceAndRule(incident.getOccurrenceNumber(), byPassedRule);
            incident.setSanction(violationType.getSanction());

        }
        incidentRepository.save(incident);


        Incident savedIncident = incidentRepository.save(incident);
        messageController.publishDiscipline(createDisciplineContent(incident                                ));

        return  new IncidentResponse(
                savedIncident.getTitle(),
                savedIncident.getDescription(),
                savedIncident.getDate(),
                savedIncident.getSanction(),
                byPassedRule.getTitle()
        );
    }

    public List<IncidentResponse> getIncidentsByDiscipline(int ownerID) {
        Discipline discipline = disciplineRepository.getDisciplineByOwnerID(ownerID);
        List<IncidentResponse> incidentResponseList = new ArrayList<>();
        List<Incident> incidentResponses = incidentRepository.getIncidentByDiscipline(discipline);

        for (Incident incident : incidentResponses) {
            IncidentResponse incidentResponse = new IncidentResponse(
                    incident.getTitle(),
                    incident.getDescription() ,
                    incident.getDate() ,
                    incident.getSanction() ,
                    incident.getRuleBypassed().getTitle()
            );
            incidentResponseList.add(incidentResponse);
        }


        return  incidentResponseList ;

    }

    //Create content to publish to kafka
    public DisciplineEvent  createDisciplineContent(Object object) {
        DisciplineEvent disciplineEvent = new DisciplineEvent();
        if (object.getClass().getName().equals("Incident")) {
            Incident incident = (Incident) object ;
            disciplineEvent.setTitle(incident.getTitle());
            //Create incident content
            disciplineEvent.setContent(createIncidentContent(incident));

        } else if (object.getClass().getName().equals("Attendance")) {
            Attendance attendance = (Attendance) object;
            disciplineEvent.setTitle("Absence");
            disciplineEvent.setContent("L'enfant n'est pas venue à l'école aujourd'hui");
            //Create Attendance Content
        }

        return  disciplineEvent ;

    }

    private String createIncidentContent(Incident incident) {
        String date = incident.getDate().toString();
        String description = incident.getDescription() ;

        return(
                "En data du " + date + " " + description + "/n"
                );
    }


}
