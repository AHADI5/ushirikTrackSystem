package com.ushirikeduc.schools.requests;

import com.ushirikeduc.schools.model.CommuniqueRecipientType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record CommuniqueRegisterRequest(

        String title,
        String content,
        String recipientType,
        String recipientGroupName  ,
        List<?> recipientIDs

) {

}


