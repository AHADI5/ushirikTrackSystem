package com.ushirikeduc.schools.requests;

import com.ushirikeduc.schools.model.AssignedTeacher;

public record ClassRegistrationRequest (

        String name ,
        int level,
        AssignedTeacher teacher
) {

}
