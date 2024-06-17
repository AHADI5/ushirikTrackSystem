package com.ushirikeduc.classservice.dto;

import java.util.Date;

public record ClassRoomEventResponse(
        String title ,
        String description ,
        String place ,
        Date startingDate ,

        Date endingDate,
        String color,
        long eventID
) {
}
