package com.ushirikeduc.classservice.service;

import Dto.ClassRoomEvent;
import Dto.StudentEvent;
import com.ushirikeduc.classservice.dto.*;
import com.ushirikeduc.classservice.model.ClassRoom;
import com.ushirikeduc.classservice.model.Student;
import com.ushirikeduc.classservice.model.Teacher;
import com.ushirikeduc.classservice.repository.ClassRoomRepository;
import com.ushirikeduc.classservice.repository.EnrolledStudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ClassRoomService{
    final ClassRoomRepository classRepository;

    final EnrolledStudentRepository enrolledStudentRepository;

//    final ClassRoomProducer classRoomProducer;

    public ClassRoomService(ClassRoomRepository classRepository,
                            EnrolledStudentRepository enrolledStudentRepository
                          ) {
        this.classRepository = classRepository;
        this.enrolledStudentRepository = enrolledStudentRepository;
//        this.classRoomProducer = classRoomProducer;
    }

    public ClassRoom registerClassRoom(ClassRegistrationRequest Request) {
        //Register a single class
        ClassRoom classRoom = ClassRoom.builder()
                .name(Request.name())
                .schoolID(Request.schoolID())
                .level((long) Request.level())
                .build();
        ClassRoom savedClassRoom =classRepository.save(classRoom) ;
        ClassRoomEvent classRoomEvent = getClassRoomEvent(classRoom);
//        classRoomProducer.sendMessage(classRoomEvent);
        return savedClassRoom;
    }


    //Register a list of classRoom

    public ResponseEntity registerClassRoomList (int schoolID,List<ClassRegistrationRequest> request) {
//        List<ClassRoom> classRoomList = new ArrayList<>();
        for (ClassRegistrationRequest registrationRequest : request) {
            ClassRoom classRoom = ClassRoom.builder()
                    .name(registrationRequest.name())
                    .level((long) registrationRequest.level())
                    .schoolID(schoolID)
                    .build();
            classRepository.save(classRoom);
        }

        return ResponseEntity.ok("Success");

    }


    private ClassRoomEvent getClassRoomEvent(ClassRoom classRoom) {
        ClassRoomEvent classRoomEvent = new ClassRoomEvent();
        classRoomEvent.setClassesID(classRoom.getClassesID()) ;
        classRoomEvent.setLevel(classRoomEvent.getLevel());
        classRoomEvent.setSchoolID(classRoomEvent.getSchoolID());
        classRoomEvent.setName(classRoomEvent.getName());
//        classRoomEvent.setTeacherName(classRoom.getTeacher().getName());
        return  classRoomEvent;
    }

    public List<ClassRoom> getAllClasses() {
        return  classRepository.findAll();
    }

    public Optional<ClassRoom> getClassById(Long classID ) {
        //Get the class by its id
        return classRepository.findById((long) Math.toIntExact(classID));
    }


    //Add classes to a school
    //Assign the teacher to the class
    public void assignTeacherToClass(Teacher teacher) {
        Optional<ClassRoom> classes = getClassById(teacher.getClassID());
        classes.ifPresent(
                c -> {
                    c.assignTeacher(teacher);
                    classRepository.save(c);
                    //todo Send teacher update
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

        //todo Publish student with class
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

    // retrieve student in  a classroom
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

    // Get Class by student ID
    public ClassInfoResponse getClassInfoByStudentID (Integer StudentID) {
        Student student = enrolledStudentRepository.findAllByStudentID(StudentID);
        ClassRoom classRoom = student.getStudentClass();

        return  classInfoResponse(classRoom);

    }
    //Create classRoom  simple request
    public ClassInfoResponse classInfoResponse(ClassRoom classRoom) {
        return new ClassInfoResponse(
                classRoom.getName(),
                Math.toIntExact(classRoom.getLevel()),
                classRoom.getName(),//make a call to school microservice to get School name.
                (int) classRoom.getSchoolID()

        );

    }

    public int getSchoolIDbyClassID(int classRoomID) {
        ClassRoom classRoom = classRepository.findById((long) classRoomID)
                .orElseThrow(() -> new RuntimeException("No class Found"));
        return (int) classRoom.getSchoolID();
    }

    public List<ClassGeneralInformation> getClassRooms(int schoolID) {
        List<ClassRoom> classRooms = classRepository.getClassRoomBySchoolID(schoolID);
        List<ClassGeneralInformation> classGeneralInformations = new ArrayList<>();
        for (ClassRoom classRoom  : classRooms) {

            ClassGeneralInformation classGeneralInformation = new ClassGeneralInformation(
                    Math.toIntExact(classRoom.getClassesID()),
                    Math.toIntExact(classRoom.getLevel()),
                    classRoom.getName(),
                    classRoom.getStudents().size(),
                    classRoom.getCourses().size(),
                    classRoom.getTeacher() == null ? " " : classRoom.getTeacher().getName()
            );

            classGeneralInformations.add(classGeneralInformation);
        }

        return classGeneralInformations;

    }
}
