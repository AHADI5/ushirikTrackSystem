package com.ushirikeduc.app.Service;

import com.ushirikeduc.app.Dto.RecipientRegisterRequest;
import com.ushirikeduc.app.Repository.RecipientRepository;
import com.ushirikeduc.app.model.NotificationRecipient;
import org.springframework.stereotype.Service;

@Service
public record RecipientService(
        RecipientRepository recipientRepository
) {
    public void registerRecipient(RecipientRegisterRequest recipientRegisterRequest) {
        NotificationRecipient notificationRecipient = NotificationRecipient
                .builder()
                .email(recipientRegisterRequest.email())
                .uniqueDeviceKey(recipientRegisterRequest.uniqueDeviceKey())
                .build();
        recipientRepository.save(notificationRecipient);

    }
}
