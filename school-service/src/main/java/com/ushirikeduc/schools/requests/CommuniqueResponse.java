package com.ushirikeduc.schools.requests;

import java.util.Date;

public record CommuniqueResponse(
        String title, String content, Date publishedDate, long communiqueID

) {
}
