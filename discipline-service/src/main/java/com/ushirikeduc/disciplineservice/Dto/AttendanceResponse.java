package com.ushirikeduc.disciplineservice.Dto;

import com.ushirikeduc.disciplineservice.model.AttendanceStatus;

import java.util.Date;

public record AttendanceResponse(
        Date attendanceDate ,
        int studentID ,
        String disciplineOwner  ,
        boolean isPresent ,
        AttendanceStatus attendanceStatus
) {
}
