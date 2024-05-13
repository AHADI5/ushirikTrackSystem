package com.ushirikeduc.schools.requests;

import com.ushirikeduc.schools.model.Address;
import com.ushirikeduc.schools.model.Director;
import com.ushirikeduc.schools.model.SchoolType;

public record SchoolResponse (
        String name  ,
        String email ,

        long schoolID ,
        SchoolType schoolType,
        DirectorResponse director ,
        Address address

) {

}
