package com.ushirikeduc.schools.service;

import Dto.StudentEvent;
import com.ushirikeduc.schools.interfaces.ClassRepository;
import com.ushirikeduc.schools.interfaces.EnrolledStudentRepository;
import com.ushirikeduc.schools.interfaces.SchoolRepository;
import com.ushirikeduc.schools.interfaces.TeacherRepository;
import com.ushirikeduc.schools.model.Classes;
import com.ushirikeduc.schools.model.EnrolledStudent;
import com.ushirikeduc.schools.model.School;
import com.ushirikeduc.schools.model.Teacher;
import com.ushirikeduc.schools.requests.ClassRegistrationRequest;
import com.ushirikeduc.schools.requests.ClassStudentsResponse;
import com.ushirikeduc.schools.requests.EnrolledStudentResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public record ClassesService(ClassRepository classRepository,
                             TeacherRepository teacherRepository,
                             SchoolRepository schoolRepository,
                             EnrolledStudentRepository enrolledStudentRepository
                             ) {


    public Classes registerClass(ClassRegistrationRequest Request) {
        //Register a signle class
        Classes classe = Classes.builder()
                .name(Request.name())
                .level((long) Request.level())
                .build();
        return classRepository.save(classe);
    }

    public List<Classes> getAllClasses() {
        return  classRepository.findAll();
    }

    public Optional<Classes> getClassById(Long classID ) {
        //Get the class by its id
        return classRepository.findById(Math.toIntExact(classID));

    }

    //Add classes to a school
    public ResponseEntity<String> addClassesToSchool (Integer schoolID ,
                                                      List<Classes> classes){
         School  school = schoolRepository.findById(schoolID)
                .orElseThrow(() -> new EntityNotFoundException("School Not found"));
        for (Classes clas : classes){
            Teacher teacher = clas.getTeacher();
            teacherRepository.save(teacher);
            clas.setSchool(school);
            classRepository.save(clas);
        }
        return ResponseEntity.ok("Classes added To school successfully");
    }

    public ResponseEntity<List<Classes>> getClassesBySchoolId(Integer schoolId){
        //Get all classes registered in a school by it's id
        School school = schoolRepository.findById((schoolId))
                .orElseThrow(() -> new EntityNotFoundException("School not found "));
        return ResponseEntity.ok(school.getClasses());

    }
    //Assign the teacher to the class
    public void assignTeacherToClass(Teacher teacher) {
        Optional<Classes> classes = getClassById(teacher.getClassID());
        classes.ifPresent(
                c -> {
                    c.assignTeacher(teacher);
                    classRepository.save(c);
                }
        );

    }

    //Adding Student to a class
    public ResponseEntity<EnrolledStudent> createStudent(StudentEvent studentEvent) {
        //find first the existing class

        EnrolledStudent enrolledStudent = new EnrolledStudent();
        enrolledStudent.setName(studentEvent.getName());
        enrolledStudent.setClassID(studentEvent.getClassID());
        enrolledStudent.setStudentID(studentEvent.getStudentID());
        enrolledStudent.setStudentClass(getClassIfExists(studentEvent.getClassID()));
        enrolledStudentRepository.save(enrolledStudent);
        return ResponseEntity.ok(enrolledStudent);
    }

    public Classes getClassIfExists(int classId) {
        Optional<Classes> existingClass = classRepository.findById(classId);
        if (existingClass.isEmpty()) {
            log.info("Class not found");
            return null; // Or throw an exception if desired
        }
        return existingClass.get();
    }

    public ResponseEntity<List<ClassStudentsResponse>> getStudentInClass(Long classId) {
        Classes studentClass = classRepository.findById(Math.toIntExact(classId))
                .orElseThrow(() -> new RuntimeException("Class not found"));

      List<EnrolledStudent> students = studentClass.getStudents();
      List<ClassStudentsResponse> studentResponses = students.stream()
                .map(this:: createSimpleClassList)
                .toList();
        return ResponseEntity.ok(studentResponses);
    }


    public ClassStudentsResponse createSimpleClassList(EnrolledStudent student) {
        return new  ClassStudentsResponse(
                student.getStudentClass().getTeacher(),
                student.getStudentClass().getName() ,
                Math.toIntExact(student.getStudentClass().getLevel()),
                student.getStudentClass().getStudents().stream()
                        .map(this::createSimpleStudent)
                        .toList()
        );
    }

    public EnrolledStudentResponse createSimpleStudent(EnrolledStudent student){
        return  new EnrolledStudentResponse(
                student.getStudentID(),
                student.getName()
        );

    }

}

