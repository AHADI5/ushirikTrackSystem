package com.ushirikeduc.app.Repository;

import com.ushirikeduc.app.model.NotificationMessageSent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationSentRepository  extends JpaRepository<NotificationMessageSent , Integer> {
}
