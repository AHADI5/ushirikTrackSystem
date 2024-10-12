package com.ushirikeduc.classservice.dto.Communication;

import java.util.List;

public record ParentPerClassRoom(
        String classRoomName  ,
        List<String> parentEmails
) {

}
