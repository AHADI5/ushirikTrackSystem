package com.ushirikeduc.teacherservice.service;


import Dto.TeacherEvent;
import com.ushirikeduc.teacherservice.controller.MessageController;

import com.ushirikeduc.teacherservice.model.Address;
import com.ushirikeduc.teacherservice.model.Teacher;
import com.ushirikeduc.teacherservice.repository.AddressRepository;
import com.ushirikeduc.teacherservice.repository.TeacherRepository;
import com.ushirikeduc.teacherservice.request.TeacherRegistrationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

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
        addressRepository.save(address);
        Teacher teacher = Teacher.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .classID(request.classID())
                .address(address)
                .email(request.email())
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
