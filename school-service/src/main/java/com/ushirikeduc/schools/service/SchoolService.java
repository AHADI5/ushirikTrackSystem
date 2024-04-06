package com.ushirikeduc.schools.service;

import com.ushirikeduc.schools.repository.AddressRepository;
import com.ushirikeduc.schools.repository.DirectorRepository;
import com.ushirikeduc.schools.repository.SchoolRepository;
import com.ushirikeduc.schools.model.Address;
import com.ushirikeduc.schools.model.Director;
import com.ushirikeduc.schools.model.School;
import com.ushirikeduc.schools.requests.SchoolRegistrationRequest;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public record SchoolService(SchoolRepository schoolRepository,
                            AddressRepository addressRepository,
                            DirectorRepository directorRepository
                            ) {
    public School registerSchool(SchoolRegistrationRequest request) {
        Address address = request.address();
        address = addressRepository.save(address);
        Address directorAddress = request.director().getAddress();
        addressRepository.save(directorAddress);

        Director director = request.director();

        director = directorRepository.save(director);
        // Calling User service for user Account creation
        System.out.println(director.getAddress());


        School school = School.builder()
                .name(request.name())
                .email(request.email())
                .postalBox(request.postalBox())
                .address(address)
                .director(director)
                .build();
        return schoolRepository.save(school);

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
