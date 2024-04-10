package com.ushirikeduc.disciplineservice.Dto;

import java.util.Date;

public record AttendanceResponse(
        Date attendanceDate ,
        boolean isPresent
) {
}
