package com.ushirikeduc.disciplineservice.Dto;

import java.util.Date;

public record AttendanceResponse(
        Date attendanceDate ,
        int studentID ,
        String disciplineOwner  ,
        boolean isPresent
) {
}
