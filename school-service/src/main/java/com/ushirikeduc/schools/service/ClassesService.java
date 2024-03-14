package com.ushirikeduc.schools.service;

import com.ushirikeduc.schools.interfaces.ClassRepository;
import com.ushirikeduc.schools.interfaces.SchoolRepository;
import com.ushirikeduc.schools.interfaces.TeacherRepository;
import com.ushirikeduc.schools.model.Classes;
import com.ushirikeduc.schools.model.School;
import com.ushirikeduc.schools.model.Teacher;
import com.ushirikeduc.schools.requests.ClassRegistrationRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public record ClassesService(ClassRepository classRepository,
                             TeacherRepository teacherRepository,
                             SchoolRepository schoolRepository
                             ) {


    public Classes registerClass(ClassRegistrationRequest Request) {
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


}
