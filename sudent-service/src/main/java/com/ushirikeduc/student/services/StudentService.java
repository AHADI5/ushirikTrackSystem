package com.ushirikeduc.student.services;

import Dto.ParentEvent;
import Dto.StudentEvent;
import com.ushirikeduc.student.controller.MessageController;
//import com.ushirikeduc.student.kafka.ParentProducer;
//import com.ushirikeduc.student.kafka.StudentProducer;
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
import java.util.Random;
import java.util.stream.Collectors;

@Service
public record StudentService(
        StudentRepository studentRepository,
        ParentRepository parentRepository,
        AddressRepository addressRepository,
        //StudentProducer studentProducer,
        MessageController messageController
//        ParentProducer parentProducer
) {

    public ResponseEntity<Student> registerNewStudent(StudentRegistrationRequest request) {
        // Check if the parent already exists
        Optional<Parent> existingParent = parentRepository.findByEmail(request.parent().getEmail());

        if (existingParent.isPresent()) {
            // Parent exists, associate student with existing parent
            Student student = createStudent(request, existingParent.get());
            Student newStudent = studentRepository.save(student);

            //Publish Student creation event

            messageController.publish(newStudent);
            return ResponseEntity.ok(student);
        } else {
            // Parent doesn't exist, create a new parent
            Parent parent = createParent(request);
            parentRepository.save(parent);

            //Create a parent Event
            ParentEvent parentEvent = getParentEvent(parent);
//            parentProducer.sendMessage(parentEvent);


            // Create a new student and associate with the new parent
            Student student = createStudent(request, parent);

            //Publish created Student


            //Publish student creation Event
//            studentProducer.sendMessage(studentEvent);
//            messageController.publish(studentEvent);
            Student newStudent = studentRepository.save(student);
            messageController.publish(newStudent);
            return ResponseEntity.ok(student);
        }



    }

    private ParentEvent getParentEvent(Parent parent) {
        String password = generatePassword(parent);
        ParentEvent parentEvent= new ParentEvent();
        parentEvent.setFirstName(parent.getFirstName());
        parentEvent.setLastName(parent.getLastName());
        parentEvent.setEmail(parent.getEmail());
        parentEvent.setPassword(password);

        return  parentEvent;
    }
    private static String generatePassword(Parent parent) {
        //Generate a random number between 10 - 100
        int randomNumber = new Random().nextInt(91)+10;
        String firstName = parent.getFirstName();
        String lastName = parent.getLastName();
        //Combine teacher information with the generated random number to form teacher's password
        return firstName.substring(0,3) + lastName.substring(0,3) +randomNumber;

    }


    private Parent createParent(StudentRegistrationRequest parentRequest) {
        return Parent.builder()
                .firstName(parentRequest.parent().getFirstName())
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
                .gender(request.gender())
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
