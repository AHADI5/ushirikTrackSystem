package com.ushirikeduc.schools.requests;

import com.ushirikeduc.schools.model.Communique;

import java.util.Date;

public record CommuniqueReviewRegisterRequest(
        String recipient ,
        Date date  ,
        boolean status ,
        Communique communique
) {
}
