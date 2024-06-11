package com.ushirikeduc.disciplineservice.Dto;

import java.util.Date;
import java.util.List;

public record AttendanceRegisterRequest(
       Date date,
       List<AttendanceSimpleForm> attendanceSimpleFormList
) {
}
