package com.ushirikeduc.classservice.dto;

import com.ushirikeduc.classservice.model.ClassRoom;

public record TeacherResponse(
        String name,
        long teacherID
) {
}
