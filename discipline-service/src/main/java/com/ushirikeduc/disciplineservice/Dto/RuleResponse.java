package com.ushirikeduc.disciplineservice.Dto;

import java.util.List;

public record RuleResponse(
        int ruleID ,
        String title ,
        String content ,
        List<ViolationResponse> violationType
) {
}
