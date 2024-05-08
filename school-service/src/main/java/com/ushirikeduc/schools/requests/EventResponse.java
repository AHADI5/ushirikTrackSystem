package com.ushirikeduc.schools.requests;

import java.time.LocalTime;
import java.util.Date;

public record EventResponse (
        String title ,
        String description ,
        String place ,
        LocalTime openingTime  ,
        LocalTime closingTime ,
        Date startingDate ,

        Date endingDate
) {
}
