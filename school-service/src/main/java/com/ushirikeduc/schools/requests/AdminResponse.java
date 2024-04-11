package com.ushirikeduc.schools.requests;

public record AdminResponse(
        String firstName ,
        String lastName ,
        String email ,

        String token
) {
}
