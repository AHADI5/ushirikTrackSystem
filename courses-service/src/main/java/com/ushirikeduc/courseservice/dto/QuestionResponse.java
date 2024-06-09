package com.ushirikeduc.courseservice.dto;

public record QuestionResponse(
        String question ,
        String description,
        String instruction ,
        String courseName ,
        double maxScore
) {
}
