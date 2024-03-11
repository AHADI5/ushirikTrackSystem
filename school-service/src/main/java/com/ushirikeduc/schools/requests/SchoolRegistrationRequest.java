package com.ushirikeduc.schools.requests;

import com.ushirikeduc.schools.model.Address;
import com.ushirikeduc.schools.model.Director;

public record SchoolRegistrationRequest(
        String name ,
        String  email ,
        String postalBox,
        Address address,
        Director director

) {
}
