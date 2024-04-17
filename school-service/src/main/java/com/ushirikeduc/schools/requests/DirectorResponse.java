package com.ushirikeduc.schools.requests;

import com.ushirikeduc.schools.model.Address;

public record DirectorResponse(
        String name ,
        int schoolID ,
        Address address
) {
}
