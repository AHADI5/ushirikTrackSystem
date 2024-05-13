package com.ushirikeduc.schools.service;

import com.ushirikeduc.schools.model.School;
import com.ushirikeduc.schools.model.SchoolAdmin;
import com.ushirikeduc.schools.repository.AdminRepository;
import com.ushirikeduc.schools.repository.SchoolRepository;
import com.ushirikeduc.schools.requests.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public record SchoolAdminService (
        SchoolRepository schoolRepository,
        AdminRepository adminRepository
) {
    public AdminResponse registerSchoolAdmin(RegisterAdminRequest request) {
        SchoolAdmin schoolAdmin  = SchoolAdmin.builder()
                .email(request.email())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .password(request.password())
                .build();
        SchoolAdmin savedSchoolAdmin = adminRepository.save(schoolAdmin);

        RestTemplate restTemplate = new RestTemplate();
        log.info("Admin is " + savedSchoolAdmin.toString());

        //Post to UserManagement Service

        return restTemplate.postForObject("http://localhost:8080/api/v1/auth/admin",
                savedSchoolAdmin,
                AdminResponse.class);

    }

    public List<SchoolResponse> getSchoolByAdminEmail(String email) {
        List<School> schools = schoolRepository.getSchoolByAdministrator_Email(email);

        List<SchoolResponse> schoolResponse = new ArrayList<>();

        for (School school : schools) {
            SchoolResponse schoolResp = new SchoolResponse(
                    school.getName(),
                    school.getEmail(),
                    school.getSchoolID(),
                    school.getSchoolType(),
                    new DirectorResponse(
                            school.getDirector().getFirstName() + " " + school.getDirector().getLastName(),
                            school.getSchoolID(),
                            school.getDirector().getAddress()
                    ),
                    school.getAddress()
            );

            schoolResponse.add(schoolResp);

        }

        return schoolResponse;
    }
}

