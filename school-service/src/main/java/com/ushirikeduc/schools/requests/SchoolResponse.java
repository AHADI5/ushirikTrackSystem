package com.ushirikeduc.schools.requests;

import com.ushirikeduc.schools.model.Address;
import com.ushirikeduc.schools.model.Director;

public record SchoolResponse (
        String name  ,
        String email ,

        int schoolID ,
        DirectorResponse director ,
        Address address

) {

}
