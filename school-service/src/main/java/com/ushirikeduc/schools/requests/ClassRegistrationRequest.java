package com.ushirikeduc.schools.requests;

import Dto.TeacherEvent;
import com.ushirikeduc.schools.model.Teacher;

public record ClassRegistrationRequest (

        String name ,
        int level,
        Teacher teacher
) {

}
