package com.ushirikeduc.schools.requests;

import java.util.Date;
import java.util.List;

public record SchoolYearSemesters(
        int semesterID ,
        Date startingDate ,
        Date endingDate  ,
        List<PeriodInSemester> periodInSemesterList
) {
}
