package com.ushirikeduc.maxmanagementservice.controller;

import com.ushirikeduc.maxmanagementservice.Dto.ClassWorkResponse;
import com.ushirikeduc.maxmanagementservice.Dto.GradedClassWorkByStudent;
import com.ushirikeduc.maxmanagementservice.Dto.ScoreRequest;
import com.ushirikeduc.maxmanagementservice.Dto.ScoreResponse;
import com.ushirikeduc.maxmanagementservice.model.Score;
import com.ushirikeduc.maxmanagementservice.repository.ClassworkRepository;
import com.ushirikeduc.maxmanagementservice.service.ClassWorkAssignedService;
import com.ushirikeduc.maxmanagementservice.service.MaxOwnerService;
import com.ushirikeduc.maxmanagementservice.service.ScoreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/score")
public class MaxController {
    final ScoreService scoreService;
    final MaxOwnerService maxOwnerService ;
    final ClassWorkAssignedService classWorkAssignedService;


    public MaxController(ScoreService scoreService, MaxOwnerService maxOwnerService, ClassWorkAssignedService classWorkAssignedService) {
        this.scoreService = scoreService;
        this.maxOwnerService = maxOwnerService;

        this.classWorkAssignedService = classWorkAssignedService;
    }

    @PostMapping("/{classworkId}/record")
    public List<Score> recordStudentMax(
            @PathVariable int classworkId , @RequestBody List<ScoreRequest> scores) {

        return scoreService.recordScore(classworkId ,scores);

    }

    @GetMapping("/{studentID}/score")
    public List<ScoreResponse> getScoresByStudentID(@PathVariable int studentID) {
        return  scoreService.getScoreByStudentID(studentID);
    }

    @GetMapping("/{classRoomID}/getWorkers")
    public ClassWorkResponse getStudentWithScore(@PathVariable int classRoomID){
        return maxOwnerService.getStudentWithScore(classRoomID);
    }

    @GetMapping("/{studentID}/getScoreByStudentID")
    public  List<GradedClassWorkByStudent> gradedClassWorkByStudentList(@PathVariable int studentID){
        return classWorkAssignedService.getRecentGradedClasswork(studentID);
    }
}
