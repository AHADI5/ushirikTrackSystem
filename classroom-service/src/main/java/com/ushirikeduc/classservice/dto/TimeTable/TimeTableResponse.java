package com.ushirikeduc.classservice.dto.TimeTable;
import java.util.List;

public record TimeTableResponse(
        long timeTableId,
        String title,
        List<CourseDayRequest> courseDayList
) {
}
