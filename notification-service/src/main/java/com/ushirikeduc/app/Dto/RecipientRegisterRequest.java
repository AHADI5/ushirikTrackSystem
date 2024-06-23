package com.ushirikeduc.app.Dto;

public record RecipientRegisterRequest(
        String email ,
        String uniqueDeviceKey
) {
}
