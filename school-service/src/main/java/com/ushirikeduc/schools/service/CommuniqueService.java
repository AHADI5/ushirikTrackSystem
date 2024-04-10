package com.ushirikeduc.schools.service;

import com.ushirikeduc.schools.model.Communique;
import com.ushirikeduc.schools.model.CommuniqueType;
import com.ushirikeduc.schools.model.School;
import com.ushirikeduc.schools.repository.CommuniqueRepository;
import com.ushirikeduc.schools.repository.SchoolRepository;
import com.ushirikeduc.schools.requests.CommuniqueRegisterRequest;
import com.ushirikeduc.schools.requests.CommuniqueResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
@Slf4j
public record CommuniqueService (
        SchoolRepository schoolRepository ,
        CommuniqueRepository communiqueRepository,
        SchoolService schoolService
) {
    public CommuniqueResponse registerCommunique(int schoolID,
                                                 CommuniqueRegisterRequest request) {
        CommuniqueType communiqueType = null ;

        switch (request.type()) {
            case  "Meeting" -> communiqueType = CommuniqueType.MEETING;
            case "Incident" -> communiqueType = CommuniqueType.INCIDENT;
            case "Event" -> communiqueType = CommuniqueType.EVENT;
            case "Convocation" -> communiqueType = CommuniqueType.CONVOCATION;
        }

        //getting the school by ID
        School school = schoolService.getSchool(schoolID);

        Communique communique = Communique.builder()
                .title(request.title())
                .content(request.content())
                .communiqueType(communiqueType)
                .date(new Date())
                .school(school)
                .build();
        Communique savedCommunique = communiqueRepository.save(communique);
        return  new CommuniqueResponse(
                savedCommunique.getTitle(),
                savedCommunique.getContent(),
                savedCommunique.getCommuniqueType(),
                savedCommunique.getDate()
        );
    }

    public List<CommuniqueResponse> getAllCommuniqueBySchoolID(int schoolID) {
        School school = schoolService.getSchool(schoolID);
        List<Communique> communiques = school.getCommuniques();
        List<CommuniqueResponse> communiqueResponses = new ArrayList<>();

        //Creating a communique simple form

        for (Communique communique : communiques)  {
            CommuniqueResponse communiqueResponse = new CommuniqueResponse(
                    communique.getTitle(),
                    communique.getContent(),
                    communique.getCommuniqueType(),
                    communique.getDate()
            );
            communiqueResponses.add(communiqueResponse);
        }
        return  communiqueResponses;

    }
}
