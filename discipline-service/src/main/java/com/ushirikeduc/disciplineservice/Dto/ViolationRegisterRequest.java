package com.ushirikeduc.disciplineservice.Dto;

public record ViolationRegisterRequest(
        String title ,
        String description ,
        int occurrenceNumber,
        String sanctionType
) {
}
