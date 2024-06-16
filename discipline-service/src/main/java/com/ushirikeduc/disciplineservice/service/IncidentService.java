package com.ushirikeduc.disciplineservice.service;

import Dto.DisciplineEvent;
import com.ushirikeduc.disciplineservice.Dto.IncidentRegisterRequest;
import com.ushirikeduc.disciplineservice.Dto.IncidentResponse;
import com.ushirikeduc.disciplineservice.Dto.OwnerIncidentsList;
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
import java.util.stream.Collectors;

@Service
public record IncidentService(
        DisciplineRepository disciplineRepository ,
        IncidentRepository incidentRepository,
        RuleRepository ruleRepository,
        ViolationRepository violationRepository,
        MessageController messageController
) {
    public IncidentResponse registerIncident(int ownerID, IncidentRegisterRequest request) {
        // Fetch the rule for the incident
        Rule byPassedRule = ruleRepository.findById(request.byPassedRule())
                .orElseThrow(() -> new ResourceNotFoundException("Rule not found"));

        // Fetch all incidents for the given owner and rule
        List<Incident> existingIncidents = incidentRepository.getAllByDisciplineOwnerIDAndRuleBypassed(ownerID, byPassedRule);

        // Find the maximum occurrence number among existing incidents
        int maxOccurrenceNumber = existingIncidents.stream()
                .mapToInt(Incident::getOccurrenceNumber)
                .max()
                .orElse(0);

        // Fetch all violation occurrences for the rule
        List<Integer> violationOccurrences = byPassedRule.getViolationList().stream()
                .map(ViolationType::getOccurrence)
                .toList();

        // Find the maximum violation occurrence
        int maxOccurrenceViolation = violationOccurrences.stream()
                .max(Integer::compareTo)
                .orElse(0);

        // Ensure occurrence number uniqueness
        int newOccurrenceNumber = maxOccurrenceNumber + 1;
        if (newOccurrenceNumber > maxOccurrenceViolation) {
            newOccurrenceNumber = 1; // Reset to 1 if exceeds maximum violation occurrence
        }

        // Create a new incident with the determined occurrence number
        Incident incident = Incident.builder()
                .date(new Date())
                .title(request.title())
                .ruleBypassed(byPassedRule)
                .description(request.Description())
                .place(request.place())
                .discipline(disciplineRepository.getDisciplineByOwnerID(ownerID))
                .occurrenceNumber(newOccurrenceNumber)
                .build();

        // Fetch the appropriate violation type based on the occurrence number and rule
        ViolationType violationType = violationRepository.findViolationTypeByOccurrenceAndRule(newOccurrenceNumber, byPassedRule);
        incident.setSanction(violationType.getSanction());

        // Save the new incident
        Incident savedIncident = incidentRepository.save(incident);

        // Publish a message related to the incident
        messageController.publishDiscipline(createDisciplineContent(savedIncident));

        // Return an IncidentResponse with details of the saved incident
        return new IncidentResponse(
                savedIncident.getTitle(),
                savedIncident.getDescription(),
                savedIncident.getDate(),
                savedIncident.getSanction(),
                byPassedRule.getTitle(),
                savedIncident.getOccurrenceNumber()
        );
    }



    public OwnerIncidentsList getIncidentsByDiscipline(int ownerID) {
        Discipline discipline = disciplineRepository.getDisciplineByOwnerID(ownerID);
        List<IncidentResponse> incidentResponseList = new ArrayList<>();
        List<Incident> incidentResponses = incidentRepository.getIncidentByDiscipline(discipline);

        for (Incident incident : incidentResponses) {
            IncidentResponse incidentResponse = new IncidentResponse(
                    incident.getTitle(),
                    incident.getDescription() ,
                    incident.getDate() ,
                    incident.getSanction() ,
                    incident.getRuleBypassed().getTitle(),
                    incident.getOccurrenceNumber()
            );
            incidentResponseList.add(incidentResponse);
        }


        return new OwnerIncidentsList(
                discipline.getOwner(),
                (int) discipline.getOwnerID(),
                incidentResponseList
        );

    }

    //Create content to publish to kafka
    public DisciplineEvent  createDisciplineContent(Incident incident) {
        DisciplineEvent disciplineEvent = new DisciplineEvent();

            disciplineEvent.setTitle(incident.getTitle());
            //Create incident content
            disciplineEvent.setContent(createIncidentContent(incident));



        return  disciplineEvent ;

    }

    private String createIncidentContent(Incident incident) {
        String date = incident.getDate().toString();
        String description = incident.getDescription() ;

        return(
                "En date du " + date + " " + description + "/n"
                );
    }


    public List<OwnerIncidentsList> getIncidentsByClassRoomId(int classRoomID) {
        List<Discipline> disciplines = disciplineRepository.getDisciplineByClassRoomID(classRoomID);
        List<OwnerIncidentsList> ownerIncidentsLists = new ArrayList<>();
        for(Discipline disciplineOwner  : disciplines) {
            OwnerIncidentsList ownerIncidentsList = new OwnerIncidentsList(
                    disciplineOwner.getOwner(),
                    (int) disciplineOwner.getOwnerID(),
                    getIncidentsListSimpleForm(disciplineOwner.getIncident())
            );
            ownerIncidentsLists.add(ownerIncidentsList);

        }

        return  ownerIncidentsLists;

    }

    private List<IncidentResponse> getIncidentsListSimpleForm(List<Incident> incidents) {
        List<IncidentResponse> incidentResponseList = new ArrayList<>();
        for (Incident incident : incidents) {
            IncidentResponse incidentResponse = new IncidentResponse(
                    incident.getTitle(),
                    incident.getDescription() ,
                    incident.getDate() ,
                    incident.getSanction() ,
                    incident.getRuleBypassed().getTitle(),
                    incident.getOccurrenceNumber()
            );
            incidentResponseList.add(incidentResponse);
        }

        return  incidentResponseList;
    }
}
