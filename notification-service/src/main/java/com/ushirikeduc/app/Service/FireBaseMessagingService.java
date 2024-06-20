package com.ushirikeduc.app.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.ushirikeduc.app.model.NotificationMessage;
import org.springframework.stereotype.Service;

@Service
public record FireBaseMessagingService(
        FirebaseMessaging firebaseMessaging

) {
    public String sendNotificationByToken(NotificationMessage notificationMessage){
        Notification notification = Notification
                .builder()
                .setTitle(notificationMessage.title())
                .setBody(notificationMessage.body())
                .build();
        Message message = Message.builder()
                .setToken(notificationMessage.recipientToken())
                .setNotification(notification)
                .build();
        try{
            firebaseMessaging.send(message);
            return  "Notification Sent";
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);

        }
    }
}
