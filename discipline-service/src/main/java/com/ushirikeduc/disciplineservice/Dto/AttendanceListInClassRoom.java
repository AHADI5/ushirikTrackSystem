package com.ushirikeduc.disciplineservice.Dto;

import java.util.Date;
import java.util.List;

public record AttendanceListInClassRoom(
        Date attendanceDate ,
        List<AttendanceResponse> simpleFormList
) {
}
