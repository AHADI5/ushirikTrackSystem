package com.ushirikeduc.schools.requests;

import java.util.Date;
import java.util.List;

public record CommuniqueResponse(
        String title, String content, com.ushirikeduc.schools.model.CommuniqueType type, Date publishedDate,
        List<ClassRoomSimpleForm> classrooms, long communiqueID

) {
}
