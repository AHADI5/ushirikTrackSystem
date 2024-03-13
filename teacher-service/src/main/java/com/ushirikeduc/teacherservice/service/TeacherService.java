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
                .name(request.name())
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
        String teacherName = savedTeacher.getName() + " "
                + savedTeacher.getFirstName() + " "
                + savedTeacher.getLastName();
        Long teacherId = savedTeacher.getId();
        int  classId = savedTeacher.getClassID();

        //Set the teacher event
        TeacherEvent teacherEvent = new TeacherEvent();
        teacherEvent.setTeacherID(teacherId);
        teacherEvent.setClassID((long) classId);
        teacherEvent.setName((teacherName));
        return teacherEvent;
    }
}
