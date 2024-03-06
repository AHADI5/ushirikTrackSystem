package com.ushirikeduc.studentservice.controller;

import com.ushirikeduc.studentservice.model.Parent;

public record StudentRegistrationRequest (
        String name ,
        String lastName ,
        String firstName,
        Parent parent
) {
}
