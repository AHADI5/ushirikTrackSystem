package com.ushirikeduc.schools.requests;

import java.util.Date;
import java.util.List;

public record SchoolYearResponse(
        String schoolYear ,
        Date startingDate ,
        Date EndingDate  ,
        List<SchoolYearSemesters> semestersList
) {


}
