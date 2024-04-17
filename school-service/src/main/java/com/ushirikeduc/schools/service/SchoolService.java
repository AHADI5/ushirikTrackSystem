package com.ushirikeduc.schools.service;

import com.ushirikeduc.schools.model.SchoolAdmin;
import com.ushirikeduc.schools.repository.AddressRepository;
import com.ushirikeduc.schools.repository.AdminRepository;
import com.ushirikeduc.schools.repository.DirectorRepository;
import com.ushirikeduc.schools.repository.SchoolRepository;
import com.ushirikeduc.schools.model.Address;
import com.ushirikeduc.schools.model.Director;
import com.ushirikeduc.schools.model.School;
import com.ushirikeduc.schools.requests.DirectorResponse;
import com.ushirikeduc.schools.requests.SchoolRegistrationRequest;
import com.ushirikeduc.schools.requests.SchoolResponse;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public record SchoolService(SchoolRepository schoolRepository,
                            AddressRepository addressRepository,
                            DirectorRepository directorRepository ,
                            AdminRepository schoolAdminRepository
                            ) {
    public SchoolResponse registerSchool(SchoolRegistrationRequest request) {
        Address address = request.address();
        address = addressRepository.save(address);
        Address directorAddress = request.director().getAddress();
        addressRepository.save(directorAddress);

        Director director = request.director();

        director = directorRepository.save(director);
        // Calling User service for user Account creation
        System.out.println(director.getAddress());

        //find Admin by email Address

        SchoolAdmin schoolAdmin = schoolAdminRepository.findSchoolAdminByEmail(request.adminEmail());


        School school = School.builder()
                .name(request.name())
                .administrator(schoolAdmin)
                .email(request.email())
                .postalBox(request.postalBox())
                .address(address)
                .director(director)
                .build();
        School savedSchool = schoolRepository.save(school);
        return new SchoolResponse(
                savedSchool.getName(),
                savedSchool.getEmail(),
                new DirectorResponse(
                        savedSchool.getDirector().getName(),
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
