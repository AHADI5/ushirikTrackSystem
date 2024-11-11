package com.ushirikeduc.disciplineservice.Dto;

public record AttendanceSimpleForm(
        int studentID  ,
        boolean isPresent ,
        String attendanceStatus
) {
}
