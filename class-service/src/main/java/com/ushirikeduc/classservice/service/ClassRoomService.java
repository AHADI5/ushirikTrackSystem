package com.ushirikeduc.classservice.service;

import Dto.ClassRoomEvent;
import Dto.StudentEvent;
import com.ushirikeduc.classservice.dto.ClassRegistrationRequest;
import com.ushirikeduc.classservice.dto.ClassStudentsResponse;
import com.ushirikeduc.classservice.dto.EnrolledStudentResponse;
import com.ushirikeduc.classservice.kafka.ClassRoomProducer;
import com.ushirikeduc.classservice.model.ClassRoom;
import com.ushirikeduc.classservice.model.Student;
import com.ushirikeduc.classservice.model.Teacher;
import com.ushirikeduc.classservice.repository.ClassRoomRepository;
import com.ushirikeduc.classservice.repository.EnrolledStudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ClassRoomService{
    final ClassRoomRepository classRepository;

    final EnrolledStudentRepository enrolledStudentRepository;

    final ClassRoomProducer classRoomProducer;

    public ClassRoomService(ClassRoomRepository classRepository,
                            EnrolledStudentRepository enrolledStudentRepository,
                            @Lazy  ClassRoomProducer classRoomProducer) {
        this.classRepository = classRepository;
        this.enrolledStudentRepository = enrolledStudentRepository;
        this.classRoomProducer = classRoomProducer;
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
        classRoomProducer.sendMessage(classRoomEvent);
        return savedClassRoom;
    }

    private ClassRoomEvent getClassRoomEvent(ClassRoom classRoom) {
        ClassRoomEvent classRoomEvent = new ClassRoomEvent();
        classRoomEvent.setClassesID(classRoom.getClassesID()) ;
        classRoomEvent.setLevel(classRoomEvent.getLevel());
        classRoomEvent.setSchoolID(classRoomEvent.getSchoolID());
        classRoomEvent.setName(classRoomEvent.getName());
        classRoomEvent.setTeacherName(classRoom.getTeacher().getName());
        return  classRoomEvent;
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