package com.ushirikeduc.classservice.dto.TimeTable;

import java.time.LocalDateTime;

public record Periods(
        LocalDateTime startTime  ,
        LocalDateTime endTime  ,
        long courseDayID   ,
        long courseID
) {

}
