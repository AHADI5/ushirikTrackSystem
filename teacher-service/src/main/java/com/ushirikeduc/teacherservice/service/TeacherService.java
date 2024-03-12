package com.ushirikeduc.teacherservice.service;

import com.ushirikeduc.teacherservice.model.Address;
import com.ushirikeduc.teacherservice.model.Teacher;
import com.ushirikeduc.teacherservice.repository.AddressRepository;
import com.ushirikeduc.teacherservice.repository.TeacherRepository;
import com.ushirikeduc.teacherservice.request.TeacherRegistrationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@Slf4j
public record TeacherService (TeacherRepository teacherRepository,
                              AddressRepository addressRepository , WebClient client) {

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
        Teacher savedTeacher = teacherRepository.save(teacher);
      //todo : make a post request to assigned teacher


        String teacherName = savedTeacher.getName();
        int teacherId = savedTeacher.getClassID();
        MultiValueMap<String , String> requestBOdy = new LinkedMultiValueMap<>();
        requestBOdy.add("id" , String.valueOf(teacherId));
        requestBOdy.add("name",teacherName);
        Mono<Void> responseMono = client.post()
                .uri("http://localhost:8081//{classID}/teacher")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(requestBOdy)
                .retrieve()
                .bodyToMono(Void.class);
        responseMono.block();



        return  savedTeacher;
    }



}
