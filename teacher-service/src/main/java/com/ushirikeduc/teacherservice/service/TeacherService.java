package com.ushirikeduc.teacherservice.service;

import com.ushirikeduc.teacherservice.model.Address;
import com.ushirikeduc.teacherservice.model.Teacher;
import com.ushirikeduc.teacherservice.repository.AddressRepository;
import com.ushirikeduc.teacherservice.repository.TeacherRepository;
import com.ushirikeduc.teacherservice.request.TeacherRegistrationRequest;
import org.springframework.stereotype.Service;

@Service
public record TeacherService (TeacherRepository teacherRepository,
                              AddressRepository addressRepository) {

    public Teacher saveTeacher(TeacherRegistrationRequest request) {
        Address address = request.address();
        addressRepository.save(address);
        Teacher teacher = Teacher.builder()
                .name(request.name())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .address(address)
                .email(request.email())
                .build();

        return teacherRepository.save(teacher);
    }

}
