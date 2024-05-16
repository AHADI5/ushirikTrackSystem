package com.ushirikeduc.schools.service;

import com.ushirikeduc.schools.model.*;
import com.ushirikeduc.schools.repository.*;
import com.ushirikeduc.schools.requests.ClassRoomSimpleForm;
import com.ushirikeduc.schools.requests.CommuniqueRegisterRequest;
import com.ushirikeduc.schools.requests.CommuniqueResponse;
import com.ushirikeduc.schools.requests.SimpleRecipient;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
                                                 CommuniqueRegisterRequest request , String token) {
        CommuniqueRecipientType communiqueRecipientTypeType = getRecipientType(request);

        //getting the school by ID
        School school = schoolService.getSchool(schoolID);
        List<Recipient> recipients = new ArrayList<>();

        assert communiqueRecipientTypeType != null;

       recipients  = communiqueRecipientTypeType.equals(CommuniqueRecipientType.INDIVIDUAL_PARENTS) ?
               getListRecipient(request.recipientIDs()) :
               getListRecipient(getConcernedParentsIDs(communiqueRecipientTypeType , (List<Long>) request.recipientIDs(), token));
        Communique communique = Communique.builder()
                .title(request.title())
                .content(request.content())
                .dateCreated(new Date())
                .recipientType(communiqueRecipientTypeType)
                .school(school)
                .recipientIDs(recipients)
                .build();
        Communique savedCommunique = communiqueRepository.save(communique);
        return  new CommuniqueResponse(
                savedCommunique.getTitle(),
                savedCommunique.getContent(),
                savedCommunique.getDateCreated(),
                savedCommunique.getCommuniqueID(),
                savedCommunique.getRecipientType(),
                getSimpleRecepientList(savedCommunique.getRecipientIDs())
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
                    communique.getTitle(),
                    communique.getContent(),
                    communique.getDateCreated(),
                    communique.getCommuniqueID(),
                    communique.getRecipientType(),
                    getSimpleRecepientList(communique.getRecipientIDs())

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

                communique.getCommuniqueID(),
                communique.getRecipientType(),
                getSimpleRecepientList(communique.getRecipientIDs())
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

    public List<String> getConcernedParentsIDs(
            CommuniqueRecipientType recipientType,
            List<Long> ids,
            String token) {

        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "http://localhost:8080/api/v1";
        List<String> parentsEmail = new ArrayList<>();

        // Set up headers with authorization token
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<List<Long>> requestEntity = new HttpEntity<>(ids, headers); // Include headers in the request

        switch (recipientType) {
            case ALL:
                String allEndpoint = baseUrl + "/student/student/AllParentEmail";
                ResponseEntity<String[]> allResponse = restTemplate.exchange(
                        allEndpoint,
                        HttpMethod.GET,
                        new HttpEntity<>(headers), // Use headers here
                        String[].class
                );
                parentsEmail.addAll(Arrays.asList(Objects.requireNonNull(allResponse.getBody())));
                break;
            case SELECTED_LEVELS:
                String levelEndpoint = baseUrl + "/classroom/studentLevel/parentEmail";
                ResponseEntity<String[]> levelResponse = restTemplate.exchange(
                        levelEndpoint,
                        HttpMethod.POST,
                        requestEntity, // Use requestEntity with headers
                        String[].class
                );
                parentsEmail.addAll(Arrays.asList(Objects.requireNonNull(levelResponse.getBody())));
                break;
            case SELECTED_SECTION:
                String sectionEndpoint = baseUrl + "/classroom/studentSection/parentEmail";
                ResponseEntity<String[]> sectionResponse = restTemplate.exchange(
                        sectionEndpoint,
                        HttpMethod.POST,
                        requestEntity, // Use requestEntity with headers
                        String[].class
                );
                parentsEmail.addAll(Arrays.asList(Objects.requireNonNull(sectionResponse.getBody())));
                break;
//            case INDIVIDUAL_PARENTS:
//                String individualEndpoint = baseUrl + "/student/studentIDs/parentEmail";
//                ResponseEntity<String[]> individualResponse = restTemplate.exchange(
//                        individualEndpoint,
//                        HttpMethod.POST,
//                        requestEntity, // Use requestEntity with headers
//                        String[].class
//                );
//                parentsEmail.addAll(Arrays.asList(Objects.requireNonNull(individualResponse.getBody())));
//                break;

            // Add other cases if needed
        }

        return parentsEmail;
    }


    public List<Recipient> getListRecipient (List<?>  emails )  {
        List<Recipient> recipients = new ArrayList<>();
        for (Object email: emails) {
            Recipient recipient = Recipient
                    .builder()
                    .recipient((String) email)
                    .build();
            Recipient savedRecipient  = recipientRepository.save(recipient);
            recipients.add(savedRecipient);

        }

        return recipients;
    }

    public  List<SimpleRecipient> getSimpleRecepientList (List<Recipient> recipients) {
        List<SimpleRecipient> simpleRecipients = new ArrayList<>();
        for (Recipient recipient: recipients) {
            simpleRecipients.add(new SimpleRecipient(recipient.getRecipient()));
        }

        return simpleRecipients;

    }

}
