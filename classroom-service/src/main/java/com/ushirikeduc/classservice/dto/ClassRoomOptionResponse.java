package com.ushirikeduc.classservice.dto;

import com.ushirikeduc.classservice.model.ClassRoom;

import java.util.List;

public record ClassRoomOptionResponse(
        long classRoomOptionID ,
        String name ,
        String description ,
        List<ClassInfoResponse> classRooms
) {
}
