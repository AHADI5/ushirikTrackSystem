package com.ushirikeduc.classservice.dto.Communication;

import java.util.List;

public record ParentPerLevel(
        int level ,
        List<ParentPerClassRoom> paretPerClassRoomList
) {
}
