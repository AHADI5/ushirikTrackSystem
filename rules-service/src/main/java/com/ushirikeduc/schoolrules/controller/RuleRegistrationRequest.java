package com.ushirikeduc.schoolrules.controller;

public record RuleRegistrationRequest(

        String title ,
        String content,
        String status ,
        String domain,
        int schoolId

) {
}
