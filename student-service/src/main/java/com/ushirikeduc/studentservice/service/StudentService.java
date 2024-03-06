package com.ushirikeduc.studentservice.service;

import com.ushirikeduc.studentservice.controller.StudentRegistrationRequest;
import com.ushirikeduc.studentservice.model.Parent;
import com.ushirikeduc.studentservice.model.Student;
import com.ushirikeduc.studentservice.repository.ParentRepository;
import com.ushirikeduc.studentservice.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public record StudentService(StudentRepository studentRepository, ParentRepository parentRepository) {

    public void registerStudent(StudentRegistrationRequest request) {
        Parent parent = Parent.builder()
                .name(request.parent().getName())
                .lastName(request.parent().getLastName())
                .phone(request.parent().getPhone())
                .email(request.parent().getEmail())
                .build();
        parentRepository.save(parent);

        Student student = Student.builder()
                .firstName(request.firstName())
                .name(request.name())
                .lastName(request.lastName())
                .parent(parent)
                .build();
        studentRepository.save(student);
    }
}
