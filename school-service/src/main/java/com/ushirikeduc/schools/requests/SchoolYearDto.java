package com.ushirikeduc.schools.requests;

import com.ushirikeduc.schools.model.Semester;

import java.util.Date;
import java.util.List;

public record SchoolYearDto(
        String schoolYear  ,
        Date startingDate ,
        Date endingDate ,
        List<Semester> semesters

) {
}
