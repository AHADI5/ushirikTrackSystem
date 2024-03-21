package com.ushirikeduc.student.services;

import com.ushirikeduc.student.model.ClassWorksAssigned;
import com.ushirikeduc.student.model.Score;
import com.ushirikeduc.student.model.Student;
import com.ushirikeduc.student.repository.ClassWorkAssignedRepository;
import com.ushirikeduc.student.repository.StudentRepository;
import com.ushirikeduc.student.repository.StudentScoreRepository;
import com.ushirikeduc.student.request.ScoreRequest;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public record ScoreService(
        StudentRepository studentRepository ,
        StudentService studentService ,
        StudentScoreRepository studentScoreRepository,
        ClassWorkAssignedRepository classWorkAssignedRepository

) {
    public List<Score> recordScore(int classWorkID , List<ScoreRequest> request) {
        //getting the classWork if exists
        ClassWorksAssigned classWork = classWorkAssignedRepository.findClassWorksAssignedByClassWorkID(classWorkID);
        //Getting the student
        List<Score> savedScoreRequests = new ArrayList<>();


        for (ScoreRequest score : request){
            Student student =  studentRepository.findById(score.studentID())
                    .orElseThrow(() -> new ResourceNotFoundException("Student's Id incorrect"));
            Score studentScore = Score.builder()
                    .score(score.score())
                    .student(student)
                    .classwork(classWork)
                    .build();
           savedScoreRequests.add(studentScoreRepository.save(studentScore));
        }

        return savedScoreRequests;



    }

}
