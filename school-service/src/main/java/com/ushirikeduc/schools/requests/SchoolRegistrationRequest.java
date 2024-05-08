package com.ushirikeduc.schools.requests;

import com.ushirikeduc.schools.model.Address;
import com.ushirikeduc.schools.model.Director;
import com.ushirikeduc.schools.model.SchoolAdmin;
import com.ushirikeduc.schools.model.SchoolType;

public record SchoolRegistrationRequest(
        String name ,
        String  email ,
        String postalBox,
        String adminEmail ,
        String schoolType ,
        SchoolAddressRegistration address,
        Director director
) {
}
