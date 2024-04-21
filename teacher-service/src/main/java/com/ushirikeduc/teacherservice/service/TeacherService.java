package com.ushirikeduc.teacherservice.service;


import Dto.TeacherEvent;
import com.ushirikeduc.teacherservice.controller.MessageController;

import com.ushirikeduc.teacherservice.model.Address;
import com.ushirikeduc.teacherservice.model.Teacher;
import com.ushirikeduc.teacherservice.repository.AddressRepository;
import com.ushirikeduc.teacherservice.repository.TeacherRepository;
import com.ushirikeduc.teacherservice.request.TeacherRegistrationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Service
@Slf4j
public record TeacherService(TeacherRepository teacherRepository,
                             AddressRepository addressRepository,

                             MessageController messageController

                             ) {


    //Register new teacher
    public Teacher saveTeacher(TeacherRegistrationRequest request) {
        Address address = request.address();
        int schoolID ;
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Integer> response = restTemplate.exchange(
                "http://localhost:8746/api/v1/classRoom/"+ request.classID() +"/schoolID",
                HttpMethod.GET,
                null,
                Integer.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            schoolID = response.getBody() != null ? response.getBody() : 0;
        } else {
            // Handle error response
            return null;
        }
        addressRepository.save(address);
        Teacher teacher = Teacher.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .classID(request.classID())
                .address(address)
                .email(request.email())
                .schoolID(schoolID)
                .build();

        Teacher savedTeacher = teacherRepository.save(teacher);

        //Get the registered Teacher with his ID and assigned Class

        messageController.publish(savedTeacher);

        //publish teacher creation
//        teacherProducer.sendMessage(teacherEvent);

        return savedTeacher;
    }


       //Generate Default password

}
