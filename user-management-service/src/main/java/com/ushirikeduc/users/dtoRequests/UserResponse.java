package com.ushirikeduc.users.dtoRequests;

import com.ushirikeduc.users.model.Role;

import java.util.Date;

public record UserResponse(
        int userID ,
        String firstName ,
        String lastName ,
        String email ,
        boolean enabled ,
        boolean isValid ,
        boolean isBlocked ,
        Role role,
        Date createdAt
) {

}
