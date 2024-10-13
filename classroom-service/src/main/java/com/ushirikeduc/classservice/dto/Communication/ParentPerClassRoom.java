package com.ushirikeduc.classservice.dto.Communication;

import java.util.List;

public record ParentPerClassRoom(
        String classRoomName  ,
        long classRoomID  ,
        List<String> parentEmails
) {

}
