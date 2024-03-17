package com.ushirikeduc.teacherservice.service;


import Dto.TeacherEvent;
import com.ushirikeduc.teacherservice.kafka.TeacherProducer;
import com.ushirikeduc.teacherservice.model.Address;
import com.ushirikeduc.teacherservice.model.Teacher;
import com.ushirikeduc.teacherservice.repository.AddressRepository;
import com.ushirikeduc.teacherservice.repository.TeacherRepository;
import com.ushirikeduc.teacherservice.request.TeacherRegistrationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
public record TeacherService(TeacherRepository teacherRepository,
                             AddressRepository addressRepository,
                             TeacherProducer teacherProducer

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

        TeacherEvent teacherEvent = getTeacherEvent(savedTeacher);

        //publish teacher creation
        teacherProducer.sendMessage(teacherEvent);

        return savedTeacher;
    }

    private static TeacherEvent getTeacherEvent(Teacher savedTeacher) {
       //Generate Default password
        String password = generatePassword(savedTeacher);
        //Set the teacher event
        TeacherEvent teacherEvent = new TeacherEvent();
        teacherEvent.setFirstName(savedTeacher.getFirstName());
        teacherEvent.setLastName(savedTeacher.getLastName());
        teacherEvent.setEmail(savedTeacher.getEmail());
        teacherEvent.setTeacherID(Math.toIntExact(savedTeacher.getId()));
        teacherEvent.setClassID(savedTeacher.getClassID());
        //Set the teacher-event  password
        teacherEvent.setPassword(password);
        return teacherEvent;
    }

    private static String generatePassword(Teacher teacher) {
        //Generate a random number between 10 - 100
        int randomNumber = new Random().nextInt(91)+10;
        String firstName = teacher.getFirstName();
        String lastName = teacher.getLastName();
        //Combine teacher information with the generated random number to form teacher's password
        return firstName.substring(0,3) + lastName.substring(0,3) +randomNumber;

    }
}
