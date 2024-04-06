package com.ushirikeduc.schools.requests;

import com.ushirikeduc.schools.service.SchoolService;

public record RuleRegistrationRequest(
        String title ,
        String content ,
        String disciplinaryDecision
) {
}
