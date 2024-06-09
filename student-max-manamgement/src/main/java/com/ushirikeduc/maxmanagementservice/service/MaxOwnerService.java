package com.ushirikeduc.maxmanagementservice.service;


import Dto.StudentEvent;
import com.ushirikeduc.maxmanagementservice.Dto.ClassWorkResponse;
import com.ushirikeduc.maxmanagementservice.Dto.ScoreStudentResponse;
import com.ushirikeduc.maxmanagementservice.model.ClassWorksAssigned;
import com.ushirikeduc.maxmanagementservice.model.MaxOwner;
import com.ushirikeduc.maxmanagementservice.model.Score;
import com.ushirikeduc.maxmanagementservice.repository.ClassworkRepository;
import com.ushirikeduc.maxmanagementservice.repository.MaxOwnerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public record MaxOwnerService(
        MaxOwnerRepository  maxOwnerRepository,
        ClassworkRepository classworkRepository

) {
    public  void RegisterOwner (StudentEvent studentEvent) {
        MaxOwner maxOwner = MaxOwner.builder()
                .ownerName(studentEvent.getName())
                .studentID(Long.valueOf(studentEvent.getStudentID()))
                .classID((long) studentEvent.getClassID())
                .build();
        maxOwnerRepository.save(maxOwner);
    }

    public ClassWorkResponse getStudentWithScore(int classWorkID) {

        ClassWorksAssigned classWorksAssigned = classworkRepository.findClassWorksAssignedByClassWorkID(classWorkID);

        return  new ClassWorkResponse(
                classWorksAssigned.getClassWorkID(),
                getScoreStudentSimpleFormat(classWorksAssigned.getScores() , classWorksAssigned)
        );

    }

    public List<ScoreStudentResponse> getScoreStudentSimpleFormat(List<Score> score , ClassWorksAssigned classWorksAssigned) {
        List<ScoreStudentResponse> scoreStudentResponses = new ArrayList<>() ;
        if (score.isEmpty()){
            List<MaxOwner> maxOwner = maxOwnerRepository.findMaxOwnersByClassID(classWorksAssigned.getClassRoomID());
            for (MaxOwner maxOwner1 : maxOwner) {
                ScoreStudentResponse scoreStudentResponse = new ScoreStudentResponse(
                        maxOwner1.getOwnerName() ,
                        Math.toIntExact(maxOwner1.getStudentID()) ,
                        classWorksAssigned.getClassWorkID(),
                        0

                );
                scoreStudentResponses.add(scoreStudentResponse);

            }
            return  scoreStudentResponses;
        }
        for(Score score1 : score ) {
            ScoreStudentResponse scoreStudentResponse = new ScoreStudentResponse(
                    score1.getStudent().getOwnerName(),
                    Math.toIntExact(score1.getStudent().getStudentID()),
                    score1.getClasswork().getClassWorkID(),
                    score1.getScore()

            );
            scoreStudentResponses.add(scoreStudentResponse);
        }

        return scoreStudentResponses;
    }
}





