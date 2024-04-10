package com.ushirikeduc.disciplineservice.Dto;

import com.ushirikeduc.disciplineservice.model.Attendance;

import java.util.List;

public record DisciplineResponse(
        long disciplineID ,
        String owner ,
        List<AttendanceResponse> attendanceResponses ,
        List<IncidentResponse> incidents


) {
}
