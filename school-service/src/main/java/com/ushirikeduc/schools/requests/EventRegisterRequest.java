package com.ushirikeduc.schools.requests;


public record EventRegisterRequest(
        String title ,

        String startingDate  ,
        String endingDate,

        String createdAt ,
        String place ,
        String description,
        String color

) {
}
