package com.ushirikeduc.disciplineservice.service;

import Dto.DisciplineEvent;
import com.ushirikeduc.disciplineservice.Dto.IncidentRegisterRequest;
import com.ushirikeduc.disciplineservice.Dto.IncidentResponse;
import com.ushirikeduc.disciplineservice.Dto.OwnerIncidentsList;
import com.ushirikeduc.disciplineservice.controller.MessageController;
import com.ushirikeduc.disciplineservice.model.*;
import com.ushirikeduc.disciplineservice.repository.*;
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
        CommuniqueDisciplineRepository communiqueDisciplineRepository ,
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
        SanctionType sanctionType = violationType.getSanctionType();
        incident.setSanction(violationType.getSanction());
        incident.setSanctionType(sanctionType);


        // Save the new incident
        Incident savedIncident = incidentRepository.save(incident);

        if ((savedIncident.getSanctionType() == SanctionType.DEF_EXCLUDE) ||
                (savedIncident.getSanctionType() == SanctionType.INVITE_PARENT) ||
                (savedIncident.getSanctionType() == SanctionType.TEMP_EXCLUDE)){
            //Create a communiqué
            DisciplineCommunique savedDisciplineCommunique = communiqueDisciplineRepository.save(createDisciplineCommunique(sanctionType , disciplineRepository.getDisciplineByOwnerID(ownerID) , savedIncident));


            //TODO PUBLISH COMMUNIQUE EVENT
        }

        // Publish a message related to the incident
        messageController.publishDiscipline(createDisciplineContent(savedIncident));

        // Return an IncidentResponse with details of the saved incident
        return new IncidentResponse(
                savedIncident.getTitle(),
                savedIncident.getDescription(),
                savedIncident.getDate(),
                savedIncident.getSanction(),
                byPassedRule.getTitle(),
                savedIncident.getSanctionType(),
                savedIncident.getOccurrenceNumber()
        );
    }

    private DisciplineCommunique createDisciplineCommunique(SanctionType sanctionType , Discipline discipline , Incident incident) {
        DisciplineCommunique communique = new DisciplineCommunique();
        switch (sanctionType) {
            case DEF_EXCLUDE ->  communique = DisciplineCommunique
                    .builder()
                    .title("Exclusion Definitive")
                    .recipient(discipline.getParentEmail())
                    .content(String.format(
                            "Nous avons le regret de vous informer de la décision prise par le conseil de discipline de l'établissement concernant votre enfant, %s, élève en classe de %s.\n\n" +
                                    "Suite à l'incident survenu le %s, impliquant %s, une enquête approfondie a été menée et des auditions ont été réalisées auprès des différentes parties concernées. " +
                                    "Après délibération, le conseil de discipline a jugé que le comportement de %s est en violation grave du règlement intérieur de notre établissement et ne peut être toléré.\n\n" +
                                    "En conséquence, nous avons le regret de vous annoncer l'exclusion définitive de %s de notre établissement à compter du %s. " +
                                    "Cette décision a été prise dans l'intérêt de la sécurité et du bien-être de tous les élèves et du personnel de l'école.\n\n" +
                                    "Nous vous recommandons de prendre contact avec les services de l'éducation nationale afin d'explorer les alternatives pour la poursuite de la scolarité de votre enfant. " +
                                    "Nous restons disponibles pour toute information complémentaire et pour faciliter cette transition dans la mesure de nos possibilités.\n\n" +
                                    "Nous comprenons que cette situation est difficile et nous souhaitons insister sur notre engagement à maintenir un environnement éducatif sûr et respectueux pour tous les élèves.\n\n" +
                                    "Nous vous prions d'agréer, Madame, Monsieur, l'expression de nos salutations distinguées.",
                            discipline.getOwner(), discipline.getClassRoomName(), incident.getDate(), incident.getDescription(), discipline.getOwner(), discipline.getOwner(), incident.getDate()
                    )

)
                    .build();
            case INVITE_PARENT -> communique = DisciplineCommunique
                    .builder()
                    .title("Convocation")
                    .content(String.format(
                            "Nous vous informons que votre enfant, %s, élève en classe de %s, est convoqué devant le conseil de discipline suite à l'incident survenu le %s impliquant %s.\n\n" +
                                    "La convocation aura lieu le %s à %s. Nous vous prions d'être présents pour discuter des faits et des mesures à prendre.\n\n" +
                                    "Veuillez agréer, Madame, Monsieur, l'expression de nos salutations distinguées.",
                            discipline.getOwner(), discipline.getClassRoomName(), incident.getDate(), incident.getDescription(), "Demain", "8heure"
                    ))
                    .recipient(discipline.getParentEmail())

                    .build();

            case TEMP_EXCLUDE -> communique = DisciplineCommunique
                    .builder()
                    .title("Exclusion temporaire")
                    .content(String.format(
                            "Nous vous informons que votre enfant, %s, élève en classe de %s, est exclu temporairement pour une durée de %s jours suite à l'incident survenu le %s impliquant %s.\n\n" +
                                    "Veuillez prendre les dispositions nécessaires pour son retour. Nous restons à votre disposition pour toute information complémentaire.\n\n" +
                                    "Veuillez agréer, Madame, Monsieur, l'expression de nos salutations distinguées.",
                            discipline.getOwner(), discipline.getClassRoomName(), "une semaine", incident.getDate(), incident.getDescription()
                    ))
                    .recipient(discipline.getParentEmail())

                    .build();
        }

        return  communique ;

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
                    incident.getSanctionType() ,
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
        List<String> emails = new ArrayList<>();

            disciplineEvent.setTitle(incident.getTitle());
            //Create incident content
            disciplineEvent.setContent(incident.getDescription());
            disciplineEvent.setSender("Discipline");
            disciplineEvent.setConcern("Remarque");
            disciplineEvent.setId((int) incident.getIncidentID());
        emails.add(incident.getDiscipline().getParentEmail());
            disciplineEvent.setRecipient(emails);

        return  disciplineEvent ;

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
                    incident.getSanctionType(),
                    incident.getOccurrenceNumber()
            );
            incidentResponseList.add(incidentResponse);
        }

        return  incidentResponseList;
    }
}
