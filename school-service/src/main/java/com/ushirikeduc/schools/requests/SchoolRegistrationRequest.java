package com.ushirikeduc.schools.requests;

import com.ushirikeduc.schools.model.Address;
import com.ushirikeduc.schools.model.Director;

public record SchoolRegistrationRequest(
        String schoolName ,
        String email ,
        Address address,
        Director director

) {
}
