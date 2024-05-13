package com.ushirikeduc.schools.requests;

import com.ushirikeduc.schools.model.Address;

public record DirectorResponse(
        String name ,
        long schoolID ,
        Address address
) {
}
