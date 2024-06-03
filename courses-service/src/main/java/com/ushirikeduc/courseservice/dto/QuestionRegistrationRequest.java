package com.ushirikeduc.courseservice.dto;

public record QuestionRegistrationRequest (
        int courseID ,
        String content ,
        String instruction,
        String description

        //todo : later , some ressources , why not web , youtube video , book
) {
}
