package com.ushirikeduc.maxmanagementservice.Dto;


import java.util.List;

public record ClassWorkResponse(
        long classWorkID ,
        List<ScoreStudentResponse> scoreList
) {
}
