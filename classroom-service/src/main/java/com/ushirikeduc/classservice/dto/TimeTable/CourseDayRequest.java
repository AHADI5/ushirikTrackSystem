package com.ushirikeduc.classservice.dto.TimeTable;

import com.ushirikeduc.classservice.model.WeekDay;

import java.util.List;

public record CourseDayRequest(
        WeekDay weekDay ,
        List<Periods> periodsList
) {

}
