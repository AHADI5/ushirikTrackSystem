package com.ushirikeduc.disciplineservice.Dto;

import java.util.Date;

public record IncidentResponse (
        String title ,
        String Description ,
        Date date ,
        String disciplineDecision ,
        String rule ,

        int occurrenceNumber


) {
}
