package com.ushirikeduc.student.services;

import Dto.StudentEvent;
import com.ushirikeduc.student.kafka.StudentProducer;
import com.ushirikeduc.student.model.Address;
import com.ushirikeduc.student.model.Parent;
import com.ushirikeduc.student.model.Student;
import com.ushirikeduc.student.repository.AddressRepository;
import com.ushirikeduc.student.repository.ParentRepository;
import com.ushirikeduc.student.repository.StudentRepository;
import com.ushirikeduc.student.request.StudentRegistrationRequest;
import com.ushirikeduc.student.request.StudentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public record StudentService(
        StudentRepository studentRepository,
        ParentRepository parentRepository,
        AddressRepository addressRepository,
        StudentProducer studentProducer
) {

    public ResponseEntity<Student> registerNewStudent(StudentRegistrationRequest request) {
        // Check if the parent already exists
        Optional<Parent> existingParent = parentRepository.findByEmail(request.parent().getEmail());

        if (existingParent.isPresent()) {
            // Parent exists, associate student with existing parent
            Student student = createStudent(request, existingParent.get());
            studentRepository.save(student);

            //Publish Student creation event
            StudentEvent studentEvent = getStudentEvent(student);
            studentProducer.sendMessage(studentEvent);
            return ResponseEntity.ok(student);
        } else {
            // Parent doesn't exist, create a new parent
            Parent parent = createParent(request);
            parentRepository.save(parent);

            // Create a new student and associate with the new parent
            Student student = createStudent(request, parent);
            StudentEvent studentEvent = getStudentEvent(student);

            //Publish student creation Event
            studentProducer.sendMessage(studentEvent);
            studentRepository.save(student);
            return ResponseEntity.ok(student);
        }



    }

    private StudentEvent getStudentEvent (Student student) {
        StudentEvent studentEvent = new StudentEvent();
        studentEvent.setStudentID(student.getStudentID());
        studentEvent.setName(student.getName());
        studentEvent.setClassID(student.getClassID());
        return studentEvent;


    }
    private Parent createParent(StudentRegistrationRequest parentRequest) {
        return Parent.builder()
                .name(parentRequest.parent().getName())
                .lastName(parentRequest.parent().getLastName())
                .phone(parentRequest.parent().getPhone())
                .email(parentRequest.parent().getEmail())
                .parentID(parentRequest.parent().getParentID())
                .build();
    }

    private Student createStudent(StudentRegistrationRequest request, Parent parent) {
        Address address = createAddress(request);
        return Student.builder()
                .name(request.name())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .classID(request.classID())
                .parent(parent)
                .address(address)
                .build();
    }

    private Address createAddress(StudentRegistrationRequest addressRequest) {
        return Address.builder()
                .houseNumber(addressRequest.address().getHouseNumber())
                .quarter(addressRequest.address().getQuarter())
                .avenue(addressRequest.address().getAvenue())
                .build();
    }





    public ResponseEntity<List<StudentResponse>> getStudentParent(int parentID) {
        Optional<Parent> parentOptional = parentRepository.findById(parentID);
        if (parentOptional.isPresent()) {
            Parent parent = parentOptional.get();
            List<Student> students = parent.getStudents();

            // Create a simplified response containing only student details
            List<StudentResponse> studentResponses = students.stream()
                    .map(this::createStudentResponse)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(studentResponses);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private StudentResponse createStudentResponse(Student student) {
        return new StudentResponse(
                student.getStudentID(),
                student.getName(),
                student.getLastName(),
                student.getFirstName(),
                student.getClassID()
        );
    }

}
