package com.ushirikeduc.schools.service;

import com.ushirikeduc.schools.model.SchoolAdmin;
import com.ushirikeduc.schools.repository.AdminRepository;
import com.ushirikeduc.schools.repository.SchoolRepository;
import com.ushirikeduc.schools.requests.AdminResponse;
import com.ushirikeduc.schools.requests.RegisterAdminRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
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

        //Post to UserManagement Service

        return restTemplate.postForObject("http://localhost:8850/api/v1/auth/admin",
                savedSchoolAdmin,
                AdminResponse.class);

    }
}
