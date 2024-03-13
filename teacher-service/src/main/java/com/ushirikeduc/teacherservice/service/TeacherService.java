package com.ushirikeduc.teacherservice.service;

import com.ushirikeduc.teacherservice.model.Address;
import com.ushirikeduc.teacherservice.model.Teacher;
import com.ushirikeduc.teacherservice.repository.AddressRepository;
import com.ushirikeduc.teacherservice.repository.TeacherRepository;
import com.ushirikeduc.teacherservice.request.TeacherRegistrationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@Slf4j
public record TeacherService(TeacherRepository teacherRepository,
                             AddressRepository addressRepository

                             ) {

    private static final RestTemplate restTemplate = new RestTemplate();

    public Teacher saveTeacher(TeacherRegistrationRequest request) {
        Address address = request.address();
        addressRepository.save(address);
        Teacher teacher = Teacher.builder()
                .name(request.name())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .classID(request.classID())
                .address(address)
                .email(request.email())
                .build();
//        webClientBuilder.baseUrl("http://localhost:8081").build();
        Teacher savedTeacher = teacherRepository.save(teacher);

        String teacherName = savedTeacher.getName();
        Long teacherId = savedTeacher.getId();
        int  classId = savedTeacher.getClassID();
        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("TeacherID", String.valueOf(teacherId));
        requestBody.add("classID" , String.valueOf(classId));
        requestBody.add("name",teacherName);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Void> responseEntity = restTemplate.postForEntity(
                "http://localhost:8081/api/v1/school/assign-teacher",
                requestBody,
                Void.class
        );


        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            System.out.println("Teacher assigned to class successfully.");
        } else {
            System.err.println("Error assigning teacher to class.");
        }

        return savedTeacher;
    }
}
