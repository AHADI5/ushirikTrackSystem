package com.ushirikeduc.schools.service;

import com.ushirikeduc.schools.interfaces.AddressInterface;
import com.ushirikeduc.schools.interfaces.DirectorInterface;
import com.ushirikeduc.schools.interfaces.SchoolInterface;
import com.ushirikeduc.schools.model.Address;
import com.ushirikeduc.schools.model.Director;
import com.ushirikeduc.schools.model.School;
import com.ushirikeduc.schools.requests.SchoolRegistrationRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public record SchoolService(SchoolInterface schoolInterface,
                            AddressInterface addressInterface,
                            DirectorInterface directorInterface
                            ) {
    public School registerSchool(SchoolRegistrationRequest request) {
        Address address = request.address();
        address = addressInterface.save(address);
        Address directorAddress = request.director().getAddress();
        addressInterface.save(directorAddress);

        Director director = request.director();

        director = directorInterface.save(director);

        School school = School.builder()
                .name(request.name())
                .email(request.email())
                .postalBox(request.postalBox())
                .address(address)
                .director(director)
                .build();
        return schoolInterface.save(school);

    }

public Optional<School> getSchool(int schoolId) {
        return  schoolInterface.findById(schoolId);
}

public Director getDirector(int schoolId) {
        Optional<School> schoolOptional = schoolInterface.findById(schoolId);
        School school = schoolOptional.get();
    return school.getDirector();
}

}
