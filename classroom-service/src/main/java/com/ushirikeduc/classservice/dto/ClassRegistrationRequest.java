package com.ushirikeduc.classservice.dto;

import com.ushirikeduc.classservice.model.ClassRoomOption;
import com.ushirikeduc.classservice.model.Teacher;

public record ClassRegistrationRequest(
        String letter ,
        int level,
        long schoolID,
        long ClassRoomOptionID
) {
}
