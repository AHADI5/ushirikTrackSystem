package com.ushirikeduc.app.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.stereotype.Service;

@Service
public record PushNotificationService (
        FirebaseMessaging firebaseMessaging
) {

//    public  String sendNotification(Note note)
}
