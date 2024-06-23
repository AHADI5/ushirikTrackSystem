package com.ushirikeduc.app.Repository;

import com.ushirikeduc.app.model.NotificationRecipient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipientRepository extends JpaRepository<NotificationRecipient, Integer> {
    NotificationRecipient findNotificationRecipientByEmail(String email);
}
