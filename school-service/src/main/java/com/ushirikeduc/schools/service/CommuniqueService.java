package com.ushirikeduc.schools.service;

import com.ushirikeduc.schools.model.*;
import com.ushirikeduc.schools.repository.*;
import com.ushirikeduc.schools.requests.ClassRoomSimpleForm;
import com.ushirikeduc.schools.requests.CommuniqueRegisterRequest;
import com.ushirikeduc.schools.requests.CommuniqueResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Slf4j
public record CommuniqueService (
        SchoolRepository schoolRepository ,
        CommuniqueRepository communiqueRepository,
        SchoolService schoolService ,
        ClassRoomService classRoomService ,

        RecipientRepository recipientRepository

) {
    public CommuniqueResponse registerCommunique(int schoolID,
                                                 CommuniqueRegisterRequest request) {
        CommuniqueRecipientType communiqueRecipientTypeType = getRecipientType(request);

        //getting the school by ID
        School school = schoolService.getSchool(schoolID);

        assert communiqueRecipientTypeType != null;
        List<Recipient> recipients = getListRecipient(getConcernedParentsIDs(communiqueRecipientTypeType , request.recipientIDs()));
        Communique communique = Communique.builder()
                .title(request.title())
                .content(request.content())
                .dateCreated(new Date())
                .school(school)
                .recipientIDs(recipients)
                .build();
        Communique savedCommunique = communiqueRepository.save(communique);
        return  new CommuniqueResponse(
                savedCommunique.getTitle(),
                savedCommunique.getContent(),
                savedCommunique.getDateCreated(),
                savedCommunique.getCommuniqueID()
        );
    }

    private static CommuniqueRecipientType getRecipientType(CommuniqueRegisterRequest request) {
        CommuniqueRecipientType communiqueRecipientTypeType = null ;

        switch (request.recipientType()) {
            case  "ALL" -> communiqueRecipientTypeType = CommuniqueRecipientType.ALL;
            case "LEVELS" -> communiqueRecipientTypeType = CommuniqueRecipientType.SELECTED_LEVELS;
            case "INDIVIDUAL" -> communiqueRecipientTypeType = CommuniqueRecipientType.INDIVIDUAL_PARENTS;
            case "SECTION" ->communiqueRecipientTypeType = CommuniqueRecipientType.SELECTED_SECTION;
        }
        return communiqueRecipientTypeType;
    }

    public List<CommuniqueResponse> getAllCommuniqueBySchoolID(int schoolID) {
        School school = schoolService.getSchool(schoolID);
        List<Communique> communiques = school.getCommuniques();
        List<CommuniqueResponse> communiqueResponses = new ArrayList<>();

        //Creating a communique simple form

        for (Communique communique : communiques)  {
            CommuniqueResponse communiqueResponse = new CommuniqueResponse(
                    communique.getTitle(), communique.getContent(),  communique.getDateCreated(),  communique.getCommuniqueID()
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

                communique.getDateCreated(),

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
        return ResponseEntity.ok("Modified");
    }

    public  CommuniqueResponse getCommuniqueByID(int communiqueID) {
        Communique communique = communiqueRepository.findById(communiqueID)
                .orElseThrow(() -> new ResourceNotFoundException("Communique not found"));
        log.info("these are classRooms from the selected communique : ");
        return getSimpleCommunique(communique);
    }

    public List<String> getConcernedParentsIDs(CommuniqueRecipientType recipientType, List<Long> ids) {
        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "http://localhost:8080/api/v1";
        List<String> parentsEmail = new ArrayList<>();

        switch (recipientType) {
            case ALL:
                String allEndpoint = baseUrl + "/student/AllParentEmail";
                ResponseEntity<String[]> allResponse = restTemplate.exchange(allEndpoint, HttpMethod.GET, null, String[].class);
                parentsEmail.addAll(Arrays.asList(Objects.requireNonNull(allResponse.getBody())));
                break;
            case SELECTED_LEVELS:
                String levelEndpoint = baseUrl + "/classroom/studentLevel/parentEmail";
                // Assuming you need to send a POST request with the list of IDs
                ResponseEntity<String[]> levelResponse = restTemplate.exchange(levelEndpoint, HttpMethod.POST, (HttpEntity<?>) ids, String[].class);
                parentsEmail.addAll(Arrays.asList(Objects.requireNonNull(levelResponse.getBody())));
                break;
            case SELECTED_SECTION:
                String sectionEndpoint = baseUrl + "/classroom/studentSection/parentEmail";
                ResponseEntity<String[]> sectionResponse = restTemplate.exchange(sectionEndpoint, HttpMethod.POST, (HttpEntity<?>) ids, String[].class);
                parentsEmail.addAll(Arrays.asList(Objects.requireNonNull(sectionResponse.getBody())));
                break;
            case INDIVIDUAL_PARENTS:
                String individualEndpoint = baseUrl + "/student/studentIDs/parentEmail";
                ResponseEntity<String[]> individualResponse = restTemplate.exchange(individualEndpoint, HttpMethod.POST, (HttpEntity<?>) ids, String[].class);
                parentsEmail.addAll(Arrays.asList(Objects.requireNonNull(individualResponse.getBody())));
                break;




            // Add other cases if needed
        }

        return parentsEmail;
    }

    public List<Recipient> getListRecipient (List<String>  emails )  {
        List<Recipient> recipients = new ArrayList<>();
        for (String email: emails) {
            Recipient recipient = Recipient
                    .builder()
                    .recipient(email)
                    .build();
            Recipient savedRecipient  = recipientRepository.save(recipient);
            recipients.add(savedRecipient);

        }

        return recipients;
    }

}
