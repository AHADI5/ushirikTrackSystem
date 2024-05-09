package com.ushirikeduc.teacherservice.service;


import com.ushirikeduc.teacherservice.controller.MessageController;

import com.ushirikeduc.teacherservice.model.Address;
import com.ushirikeduc.teacherservice.model.Teacher;
import com.ushirikeduc.teacherservice.repository.AddressRepository;
import com.ushirikeduc.teacherservice.repository.TeacherRepository;
import com.ushirikeduc.teacherservice.request.TeacherRegistrationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public record TeacherService(TeacherRepository teacherRepository,
                             AddressRepository addressRepository,

                             MessageController messageController

                             ) {


    //Register new teacher
    public Teacher saveTeacher(TeacherRegistrationRequest request) {
        Address address = request.address();

//        RestTemplate restTemplate = new RestTemplate();
//
//        ResponseEntity<Integer> response = restTemplate.exchange(
//                "http://localhost:8746/api/v1/classroom/"+ request.classID() +"/schoolID",
//                HttpMethod.GET,
//                null,
//                Integer.class
//        );
//        log.info("School Id .......... ........ ...... is " + response.getBody());
//        int schoolID = response.getBody() == null ? 0 : response.getBody() ;
//        log.info("again " + schoolID);


        addressRepository.save(address);
        Teacher teacher = Teacher.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
//                .classID(request.classID())
                .address(address)
                .email(request.email())
                .schoolID(request.schoolID())
                .build();

        Teacher savedTeacher = teacherRepository.save(teacher);

        //Get the registered Teacher with his ID and assigned Class

        messageController.publish(savedTeacher);

        //publish teacher creation
//        teacherProducer.sendMessage(teacherEvent);

        return savedTeacher;
    }

    public ResponseEntity<String> modifyTeacherInformation(String userEMail ,
                                                           TeacherRegistrationRequest request) {
        Teacher teacher = teacherRepository.findTeacherByEmail(userEMail);
        teacher.setAddress(request.address());
        teacher.setEmail(request.email());
        teacher.setFirstName(request.firstName());
        teacher.setLastName(request.lastName());

//        teacher.setClassID(request.classID());

        Teacher newTeacher  = teacherRepository.save(teacher);
        messageController.publish(newTeacher);
        return ResponseEntity.ok("Modified Successfully");
    }



}
