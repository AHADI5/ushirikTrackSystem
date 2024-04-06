package com.ushirikeduc.schools.requests;

import java.util.Date;

public record CommuniqueResponse(
        String title ,
        String content ,
        com.ushirikeduc.schools.model.CommuniqueType type,
        Date publishedDate
) {
}
