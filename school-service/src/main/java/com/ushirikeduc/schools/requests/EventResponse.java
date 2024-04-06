package com.ushirikeduc.schools.requests;

import java.util.Date;

public record EventResponse (
        String title ,
        String description ,
        Date startingDate ,
        Date endingDate
) {
}
