package com.ushirikeduc.schools.requests;

import com.ushirikeduc.schools.model.SchoolYearStatus;

import java.util.Date;
import java.util.List;

public record SchoolYearResponse(
        long schoolYearID  ,
        String schoolYear ,
        Date startingDate ,
        Date EndingDate  ,
        SchoolYearStatus schoolYearStatus ,
        List<SchoolYearSemesters> semestersList
) {


}
