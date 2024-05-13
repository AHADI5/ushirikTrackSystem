package com.ushirikeduc.schools.requests;

import com.ushirikeduc.schools.model.CommuniqueRecipientType;

import java.util.List;
import java.util.Map;

public record CommuniqueRegisterRequest(
        Long typeID ,
        String title ,
        String content,
        String recipientType ,

        List<Long> recipientIDs




) {
}


