package com.ushirikeduc.schools.service;

import com.ushirikeduc.schools.controller.MessageController;
import com.ushirikeduc.schools.model.SchoolAdmin;
import com.ushirikeduc.schools.repository.AddressRepository;
import com.ushirikeduc.schools.repository.AdminRepository;
import com.ushirikeduc.schools.repository.DirectorRepository;
import com.ushirikeduc.schools.repository.SchoolRepository;
import com.ushirikeduc.schools.model.Address;
import com.ushirikeduc.schools.model.Director;
import com.ushirikeduc.schools.model.School;
import com.ushirikeduc.schools.requests.DirectorResponse;
import com.ushirikeduc.schools.requests.SchoolAddressRegistration;
import com.ushirikeduc.schools.requests.SchoolRegistrationRequest;
import com.ushirikeduc.schools.requests.SchoolResponse;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.aspectj.bridge.Message;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public record SchoolService(SchoolRepository schoolRepository,
                            AddressRepository addressRepository,
                            DirectorRepository directorRepository ,
                            AdminRepository schoolAdminRepository,
                            MessageController messageController
                            ) {
    public SchoolResponse registerSchool(SchoolRegistrationRequest request) {
        SchoolAddressRegistration addressRegistration = request.address();
        Address address = Address.builder()
                .quarter(addressRegistration.schoolQuarter())
                .avenue(addressRegistration.schoolAvenue())
                .build();

        Address savedAddress = addressRepository.save(address);
        Address directorAddress = request.director().getAddress();
        addressRepository.save(directorAddress);

        Director directorRequest = request.director();
        Director director = Director.builder()
                .lastName(directorRequest.getLastName())
                .firstName(directorRequest.getFirstName())
                .directorEmail(directorRequest.getDirectorEmail())
                .address(directorAddress)
                .build();



        Director savedDirector = directorRepository.save(director);
        // Calling User service for user Account creation


//        System.out.println(director.getAddress());

        //find Admin by email Address

        SchoolAdmin schoolAdmin = schoolAdminRepository.findSchoolAdminByEmail(request.adminEmail());


        School school = School.builder()
                .name(request.name())
                .administrator(schoolAdmin)
                .email(request.email())
                .postalBox(request.postalBox())
                .address(savedAddress)
                .director(savedDirector)
                .build();
        School savedSchool = schoolRepository.save(school);

        //Getting schoolID
        int schoolID = savedSchool.getSchoolID();
        savedDirector.setSchoolID(schoolID);
        directorRepository.save(savedDirector);
        //Creating director account
        messageController.publishDirector(savedDirector);

        return new SchoolResponse(
                savedSchool.getName(),
                savedSchool.getEmail(),
                school.getSchoolID(),
                new DirectorResponse(
                        savedSchool.getDirector().getFirstName() + " " + savedSchool.getDirector().getLastName(),
                        savedSchool.getSchoolID(),
                        savedSchool.getDirector().getAddress()
                ),
                savedSchool.getAddress()
        );

    }

public School getSchool(int schoolId) {
    return schoolRepository.findById(schoolId)
                .orElseThrow(() -> new ResourceNotFoundException(("School Not found")));
}

public Director getDirector(int schoolId) {
        Optional<School> schoolOptional = schoolRepository.findById(schoolId);
        School school = schoolOptional.get();
    return school.getDirector();
}



}
