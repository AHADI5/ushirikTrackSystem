package com.ushirikeduc.student.controller;

import com.ushirikeduc.student.model.Score;
import com.ushirikeduc.student.model.Student;
import com.ushirikeduc.student.request.*;
import com.ushirikeduc.student.services.ClassWorkAssignedService;
import com.ushirikeduc.student.services.ScoreService;
import com.ushirikeduc.student.services.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/v1/student")
public record StudentController(
        StudentService studentService,
        ClassWorkAssignedService classWorkAssignedService,
        ScoreService scoreService
) {
    @PostMapping("/register-student")
    public ResponseEntity<Student> registerStudent(
            @RequestBody StudentRegistrationRequest request
            ) {
        return studentService.registerNewStudent(request);
    }
    @GetMapping("/parent/{parentId}/students")
    public ResponseEntity<List<StudentResponse>> getStudentByParent(
            @PathVariable Integer parentId
    ) {
        return  studentService.getStudentParent(parentId);
    }

    @PostMapping("parent/students")
    public List<Student> getStudentByParent (@RequestBody StudentByParentEmailRequest request) {
        return studentService.getStudentByParentEmail(request);

    }

    @PostMapping ("parentEmail/students")
    public ResponseEntity<List<StudentWithClassResponse>> getStudentByParentEmail(
            @RequestBody StudentByParentEmailRequest emailAddress
    ) {

        List<Student> students = studentService.getStudentByParentEmail(emailAddress);

        //Map each student to a StudentResponse object containing their class info

        List<StudentWithClassResponse> studentWithClassInfo = new ArrayList<>();
        for(Student student : students) {
            //Retrieve class information for current student
            ClassStudentsResponse classInfo = fetchClassInfoForStudent(student.getStudentID());

            //Create a StudentResponse object with class informartions
            assert classInfo != null;
            StudentWithClassResponse studentResponseWithClassInfo = new StudentWithClassResponse(
                    student.getStudentID(),
                    student.getName(),
                    student.getLastName(),
                    student.getFirstName(),
                    student.getClassID(),
                    classInfo.getClassName(),
                    classInfo.getLevel(),
                    classInfo.getSchoolName() ,
                    classInfo.getSchoolID()

            );
            studentWithClassInfo.add(studentResponseWithClassInfo);
        }
        return ResponseEntity.ok( studentWithClassInfo);

    }


    public ClassStudentsResponse fetchClassInfoForStudent(int studentID) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<ClassStudentsResponse> response = restTemplate.exchange(
                "http://localhost:8746/api/v1/classroom/" + studentID + "/class",
                HttpMethod.GET,
                null,
                ClassStudentsResponse.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            // Handle error response
            return null;
        }

    }

    @PostMapping("parent/studentNumber")
    public ResponseEntity<Integer>  getStudentNumberByParent(@RequestBody
                                    StudentByParentEmailRequest emailAddress) {
        return studentService.getStudentNumberByParent(emailAddress) ;



    }


}
