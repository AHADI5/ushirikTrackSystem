package com.ushirikeduc.schools.requests;

import java.util.Date;

public record PeriodInSemester(
        int periodID ,
        Date startingDate ,
        Date endingDate
) {
}
