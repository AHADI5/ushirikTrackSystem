package com.ushirikeduc.classservice.service;

import Dto.StudentEvent;
import com.ushirikeduc.classservice.dto.ClassRegistrationRequest;
import com.ushirikeduc.classservice.dto.ClassStudentsResponse;
import com.ushirikeduc.classservice.dto.EnrolledStudentResponse;
import com.ushirikeduc.classservice.model.ClassRoom;
import com.ushirikeduc.classservice.model.Student;
import com.ushirikeduc.classservice.model.Teacher;
import com.ushirikeduc.classservice.repository.ClassRoomRepository;
import com.ushirikeduc.classservice.repository.CourseRepository;
import com.ushirikeduc.classservice.repository.EnrolledStudentRepository;
import com.ushirikeduc.classservice.repository.TeacherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public record ClassRoomService(ClassRoomRepository classRepository,
                               TeacherRepository teacherRepository,

                               EnrolledStudentRepository enrolledStudentRepository,
                               CourseRepository courseRepository
) {


    public ClassRoom registerClassRoom(ClassRegistrationRequest Request) {
        //Register a signle class
        ClassRoom classe = ClassRoom.builder()
                .name(Request.name())
                .schoolID(Request.schoolID())
                .level((long) Request.level())
                .build();
        return classRepository.save(classe);
    }

    public List<ClassRoom> getAllClasses() {
        return  classRepository.findAll();
    }

    public Optional<ClassRoom> getClassById(Long classID ) {
        //Get the class by its id
        return classRepository.findById(Long.valueOf(Math.toIntExact(classID)));

    }

    //Add classes to a school



    //Assign the teacher to the class
    public void assignTeacherToClass(Teacher teacher) {
        Optional<ClassRoom> classes = getClassById(teacher.getClassID());
        classes.ifPresent(
                c -> {
                    c.assignTeacher(teacher);
                    classRepository.save(c);
                }
        );

    }

    //Adding Student to a class
    public ResponseEntity<Student> createStudent(StudentEvent studentEvent) {
        //find first the existing class

        Student enrolledStudent = new Student();
        enrolledStudent.setName(studentEvent.getName());
        enrolledStudent.setGender(studentEvent.getGender());
//        enrolledStudent.setClassID(studentEvent.getClassID());
        enrolledStudent.setStudentID(studentEvent.getStudentID());
        enrolledStudent.setStudentClass(getClassIfExists(studentEvent.getClassID()));
        enrolledStudentRepository.save(enrolledStudent);
        return ResponseEntity.ok(enrolledStudent);
    }

    public ClassRoom getClassIfExists(Integer classId) {
        Optional<ClassRoom> existingClass = classRepository.findById(Long.valueOf(classId));
        if (existingClass.isEmpty()) {
            log.info("Class not found");
            return null; // Or throw an exception if desired
        }
        return existingClass.get();
    }

    public ResponseEntity<List<ClassStudentsResponse>> getStudentInClass(Long classId) {
        ClassRoom studentClass = classRepository.findById((long) Math.toIntExact(classId))
                .orElseThrow(() -> new RuntimeException("Class not found"));

        List<Student> students = studentClass.getStudents();
        List<ClassStudentsResponse> studentResponses = students.stream()
                .map(this:: createSimpleClassList)
                .toList();
        return ResponseEntity.ok(studentResponses);
    }


    public ClassStudentsResponse createSimpleClassList(Student student) {
        return new  ClassStudentsResponse(
                student.getStudentClass().getTeacher(),
                student.getStudentClass().getName() ,
                Math.toIntExact(student.getStudentClass().getLevel()),
                student.getStudentClass().getStudents().stream()
                        .map(this::createSimpleStudent)
                        .toList()
        );
    }

    public EnrolledStudentResponse createSimpleStudent(Student student){
        return  new EnrolledStudentResponse(
                (int) student.getStudentID(),
                student.getName()
        );

    }



}