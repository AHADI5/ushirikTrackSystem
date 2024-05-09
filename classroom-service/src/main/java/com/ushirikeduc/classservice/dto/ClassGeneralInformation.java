package com.ushirikeduc.classservice.dto;

public record ClassGeneralInformation(
        int classRoomID ,
        int level ,
        String letter ,
        int studentNumber,

        int courseNumber ,

        String optionName
//        String teacherName

) {
}
