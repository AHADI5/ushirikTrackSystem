package com.ushirikeduc.classservice.dto;

import com.ushirikeduc.classservice.model.Teacher;

public record ClassRegistrationRequest(
        String name ,
        int level,
        long schoolID
) {
}
