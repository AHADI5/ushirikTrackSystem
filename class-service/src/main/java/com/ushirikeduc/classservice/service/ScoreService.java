package com.ushirikeduc.classservice.service;

import com.ushirikeduc.classservice.dto.ScoreRegistrationRequest;
import com.ushirikeduc.classservice.model.Student;
import com.ushirikeduc.classservice.repository.ClassWorkRepository;
import com.ushirikeduc.classservice.repository.EnrolledStudentRepository;
import com.ushirikeduc.classservice.repository.ScoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public record ScoreService(
        ScoreRepository scoreRepository ,
        EnrolledStudentRepository enrolledStudentRepository,
        ClassWorkRepository classWorkRepository
) {
    public Score recordScore (int studentID , int classworkID, ScoreRegistrationRequest request){
        Student student = enrolledStudentRepository.findById(Long.valueOf(studentID))
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        ClassWork classWork = classWorkRepository.findById(classworkID)
                .orElseThrow(() ->  new ResourceNotFoundException("not found"));
        Score score = Score.builder()
                .score(request.score())
                .classwork(classWork)
                .student(student)
                .build();
        return   scoreRepository.save(score);



    }

//    public List<Score> getScoreByStudentID(int studentID) {
//        return scoreRepository.getScoresByStudentId(studentID);
//
//    }

}
