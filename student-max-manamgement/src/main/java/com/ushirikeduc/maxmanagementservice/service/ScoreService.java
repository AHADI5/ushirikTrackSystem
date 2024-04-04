package com.ushirikeduc.maxmanagementservice.service;

import com.ushirikeduc.maxmanagementservice.Dto.ScoreRequest;
import com.ushirikeduc.maxmanagementservice.Dto.ScoreResponse;
import com.ushirikeduc.maxmanagementservice.model.ClassWorksAssigned;
import com.ushirikeduc.maxmanagementservice.model.MaxOwner;
import com.ushirikeduc.maxmanagementservice.model.Score;
import com.ushirikeduc.maxmanagementservice.repository.ClassworkRepository;
import com.ushirikeduc.maxmanagementservice.repository.MaxOwnerRepository;
import com.ushirikeduc.maxmanagementservice.repository.ScoreRepository;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public record ScoreService(
       ClassworkRepository classworkRepository ,
       MaxOwnerRepository maxOwnerRepository ,
       ScoreRepository scoreRepository
) {
    public List<Score> recordScore(int classWorkID , List<ScoreRequest> request) {
        //getting the classWork if exists
        ClassWorksAssigned classWork = classworkRepository.findClassWorksAssignedByClassWorkID(classWorkID);
        //Getting the student
        List<Score> savedScoreRequests = new ArrayList<>();

        for (ScoreRequest score : request){
            MaxOwner student =  maxOwnerRepository.findMaxOwnerByStudentID((long) score.studentID());
//                    .orElseThrow(() -> new ResourceNotFoundException("Student's Id incorrect"));
            Score studentScore = Score.builder()
                    .score(score.score())
                    .student(student)
                    .classwork(classWork)
                    .build();
           savedScoreRequests.add(scoreRepository.save(studentScore));
        }
        return savedScoreRequests;
    }
    public List<ScoreResponse> getScoreByStudentID(int studentID){
        MaxOwner student = maxOwnerRepository.findMaxOwnerByStudentID((long) studentID);
//                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
        List<Score> studentScore = scoreRepository.getScoreByStudent(student);
        List<ScoreResponse> studentScoreList =new ArrayList<>() ;

        for (Score score : studentScore) {
            int ScoreID = Math.toIntExact(score.getId());
            //find Real Score
            Score realScore = scoreRepository.findById(ScoreID)
                    .orElseThrow(() -> new ResourceNotFoundException("Score Not found"));

            ScoreResponse scoreResponse = new ScoreResponse(
                    realScore.getClasswork().getCourseName(),realScore.getClasswork().getTitle(), realScore.getScore()
            );
            studentScoreList.add(scoreResponse);
        }
        return  studentScoreList;
    }

    //Get given score for a given course
}
