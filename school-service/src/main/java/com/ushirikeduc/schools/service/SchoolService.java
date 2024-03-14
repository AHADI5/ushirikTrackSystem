package com.ushirikeduc.schools.service;

import com.ushirikeduc.schools.interfaces.AddressRepository;
import com.ushirikeduc.schools.interfaces.DirectorRepository;
import com.ushirikeduc.schools.interfaces.SchoolRepository;
import com.ushirikeduc.schools.model.Address;
import com.ushirikeduc.schools.model.Director;
import com.ushirikeduc.schools.model.School;
import com.ushirikeduc.schools.requests.SchoolRegistrationRequest;
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

public Optional<School> getSchool(int schoolId) {
        return  schoolRepository.findById(schoolId);
}

public Director getDirector(int schoolId) {
        Optional<School> schoolOptional = schoolRepository.findById(schoolId);
        School school = schoolOptional.get();
    return school.getDirector();
}



}
