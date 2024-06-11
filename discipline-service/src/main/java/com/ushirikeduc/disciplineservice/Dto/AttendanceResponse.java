package com.ushirikeduc.disciplineservice.Dto;

import java.util.Date;

public record AttendanceResponse(
        Date attendanceDate ,
        String disciplineOwner  ,
        boolean isPresent
) {
}
