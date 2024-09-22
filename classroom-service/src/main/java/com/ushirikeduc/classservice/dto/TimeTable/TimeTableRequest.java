package com.ushirikeduc.classservice.dto.TimeTable;
import com.ushirikeduc.classservice.model.TimeTableCategory;
import java.util.List;

public record TimeTableRequest(
        String title  ,
        TimeTableCategory timeTableCategory ,
        List<CourseDayRequest> courseDayList
) {

}
