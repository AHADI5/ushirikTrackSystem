package com.ushirikeduc.schools.service;

import com.ushirikeduc.schools.model.ClassRoom;
import com.ushirikeduc.schools.model.Communique;
import com.ushirikeduc.schools.model.CommuniqueType;
import com.ushirikeduc.schools.model.School;
import com.ushirikeduc.schools.repository.CommuniqueRepository;
import com.ushirikeduc.schools.repository.SchoolRepository;
import com.ushirikeduc.schools.requests.ClassRoomSimpleForm;
import com.ushirikeduc.schools.requests.CommuniqueRegisterRequest;
import com.ushirikeduc.schools.requests.CommuniqueResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public record CommuniqueService (
        SchoolRepository schoolRepository ,
        CommuniqueRepository communiqueRepository,
        SchoolService schoolService ,
        ClassRoomService classRoomService
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

        //List of concerned classRoom an Empty list
        List<ClassRoom> concernedClassRooms  = new ArrayList<>();

        //make a list of concerned classRooms from the request
        for (int classRoomID : request.classConcerned()) {
            ClassRoom classRoom = classRoomService.getClassRoomByID(classRoomID);
            concernedClassRooms.add(classRoom);

        }

        Communique communique = Communique.builder()
                .title(request.title())
                .content(request.content())
                .communiqueType(communiqueType)
                .dateCreated(new Date())
                .school(school)
                .classrooms(concernedClassRooms)
                .build();
        Communique savedCommunique = communiqueRepository.save(communique);
        return  new CommuniqueResponse(
                savedCommunique.getTitle(),
                savedCommunique.getContent(),
                savedCommunique.getCommuniqueType(),
                savedCommunique.getDateCreated(),
                getClassRoomSimpleForm(savedCommunique.getClassrooms()),
                savedCommunique.getCommuniqueID()
        );
    }

    public List<CommuniqueResponse> getAllCommuniqueBySchoolID(int schoolID) {
        School school = schoolService.getSchool(schoolID);
        List<Communique> communiques = school.getCommuniques();
        List<CommuniqueResponse> communiqueResponses = new ArrayList<>();

        //Creating a communique simple form

        for (Communique communique : communiques)  {
            CommuniqueResponse communiqueResponse = new CommuniqueResponse(
                    communique.getTitle(), communique.getContent(), communique.getCommuniqueType(), communique.getDateCreated(),  getClassRoomSimpleForm(communique.getClassrooms()), communique.getCommuniqueID()
            );
            communiqueResponses.add(communiqueResponse);
        }
        return  communiqueResponses;

    }

    public List<CommuniqueResponse> getRecentCommunique(int schoolID) {
        List<CommuniqueResponse> communiqueResponses = new ArrayList<>();
//        Pageable topFive = (Pageable) PageRequest.of(0, 5, Sort.by("dateCreated").descending());
        //FIND THE SCHOOL
        School school = schoolService.getSchool(schoolID);

        List<Communique> communiques =  communiqueRepository.findTop1BySchoolOrderByDateCreatedDesc( school,PageRequest.of(0, 1));
        for (Communique communique : communiques) {
            communiqueResponses.add(getSimpleCommunique(communique));
        }
        return  communiqueResponses ;
    }

    public CommuniqueResponse getSimpleCommunique(Communique communique) {

        return new CommuniqueResponse(
                communique.getTitle(),
                communique.getContent(),
                communique.getCommuniqueType(),
                communique.getDateCreated(),
                getClassRoomSimpleForm(communique.getClassrooms()),
                communique.getCommuniqueID()
        );
    }

    public List<ClassRoomSimpleForm> getClassRoomSimpleForm (List<ClassRoom> classRooms) {
        List<ClassRoomSimpleForm> classRoomSimpleForms = new ArrayList<>();
        for (ClassRoom classRoom : classRooms) {
            ClassRoomSimpleForm classRoom1 = new ClassRoomSimpleForm(
                    classRoom.getLevel(),
                    classRoom.getName(),
                    classRoom.getSchoolID()

            );
            classRoomSimpleForms.add(classRoom1);

        }
      return  classRoomSimpleForms;

    }

    public ResponseEntity<String> deleteCommunique(int communiqueID) {
        //find communique

        Communique communique = communiqueRepository.findById((int) communiqueID)
                .orElseThrow(() -> new ResourceNotFoundException("No communique found"));
        communiqueRepository.deleteById(communiqueID);
        return ResponseEntity.ok("Deleted");
    }

    public ResponseEntity<String> updateCommunique(int communiqeID , CommuniqueRegisterRequest updatedCommunique) {
        Communique communique = communiqueRepository.findById((int) communiqeID)
                .orElseThrow(() -> new ResourceNotFoundException("no communique found"));
        communique.setTitle(updatedCommunique.title());
        communique.setContent(updatedCommunique.content());
        List <ClassRoom> classRoomList = new ArrayList<>();

        for (int classRoomID : updatedCommunique.classConcerned()) {
            ClassRoom classRoom = classRoomService.getClassRoomByID(classRoomID);
            classRoomList.add(classRoom);
        }

        communique.setClassrooms(classRoomList);

        return ResponseEntity.ok("Modified");

    }

    public  CommuniqueResponse getCommuniqueByID(int communiqueID) {
        Communique communique = communiqueRepository.findById(communiqueID)
                .orElseThrow(() -> new ResourceNotFoundException("Communique not found"));

        log.info("these are classRooms from the selected communique : " + communique.getClassrooms().toString());

        return getSimpleCommunique(communique);

    }
}
