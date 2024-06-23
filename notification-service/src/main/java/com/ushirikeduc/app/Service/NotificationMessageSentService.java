package com.ushirikeduc.app.Service;

import com.ushirikeduc.app.Repository.NotificationSentRepository;

public record NotificationMessageSentService(
        NotificationSentRepository notificationSentRepository
) {
}
