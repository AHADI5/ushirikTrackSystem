package com.ushirikeduc.disciplineservice.Dto;

import com.ushirikeduc.disciplineservice.model.ViolationType;

import java.util.List;

public record RuleRegisterRequest (
        String title ,
        int schoolID ,
        String content ,
        List<ViolationRegisterRequest>  violation
){
}
