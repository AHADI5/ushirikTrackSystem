package com.ushirikeduc.disciplineservice.Dto;

import com.ushirikeduc.disciplineservice.model.SanctionType;

import java.util.Date;

public record IncidentResponse (
        String title ,
        String Description ,
        Date date ,
        String disciplineDecision ,
        String rule ,
        SanctionType sanctionType ,

        int occurrenceNumber


) {
}
