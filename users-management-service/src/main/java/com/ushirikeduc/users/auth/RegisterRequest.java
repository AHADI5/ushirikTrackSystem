package com.ushirikeduc.users.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private int userID;
    private String firstName ;
    private String lastName ;
    private String email ;
    private String password ;

}
