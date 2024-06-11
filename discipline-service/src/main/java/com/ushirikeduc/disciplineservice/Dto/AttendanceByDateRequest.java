package com.ushirikeduc.disciplineservice.Dto;

import java.util.Date;

public record AttendanceByDateRequest(
        Date date,
        int classRoomId
) {
}
