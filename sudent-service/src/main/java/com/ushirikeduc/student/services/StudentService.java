package com.ushirikeduc.student.services;

import com.ushirikeduc.student.model.Address;
import com.ushirikeduc.student.model.Parent;
import com.ushirikeduc.student.model.Student;
import com.ushirikeduc.student.repository.AddressRepository;
import com.ushirikeduc.student.repository.ParentRepository;
import com.ushirikeduc.student.repository.StudentRepository;
import com.ushirikeduc.student.request.StudentRegistrationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public record StudentService(
        StudentRepository studentRepository,
        ParentRepository parentRepository,
        AddressRepository addressRepository) {


    public ResponseEntity<Student> registerNewStudent(StudentRegistrationRequest request) {
        // todo : save student information's to database
        Parent parent = Parent.builder()
                .students(request.parent().getStudents())
                .phone(request.parent().getPhone())
                .email(request.parent().getEmail())
                .parentID(request.parent().getParentID())
                .build();
        parentRepository.save(parent);

        Address address = Address.builder()
                .houseNumber(request.address().getHouseNumber())
                .quarter(request.address().getQuarter())
                .avenue(request.address().getAvenue())
                .houseNumber(request.address().getHouseNumber())
                .build();
        addressRepository.save(address);



        Student student = Student.builder()
                .name(request.name())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .classID(request.classID())
                .parent(parent)
                .address(address)
                .build();
        studentRepository.save(student);
        // todo : publish student Creation Event
        // todo :


        return ResponseEntity.ok(studentRepository.save(student));
    }

    public ResponseEntity<List<Student>> getStudentByparent(int parentID){
        Optional<Parent> parentOptional = parentRepository.findById(parentID);
        if (parentOptional.isPresent()){
            Parent parent = parentOptional.get();
            List<Student> students = parent.getStudents();
            return  ResponseEntity.ok(students);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
