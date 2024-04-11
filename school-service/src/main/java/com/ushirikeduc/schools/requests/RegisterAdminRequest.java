package com.ushirikeduc.schools.requests;

public record RegisterAdminRequest(
        String firstName ,
        String lastName ,
        String email ,
        String password
) {
}
