package com.ushirikeduc.app.Service;

import com.google.firebase.messaging.*;
import com.ushirikeduc.app.Dto.NotificationMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public record FireBaseMessagingService(
        FirebaseMessaging firebaseMessaging

) {

    public void sendNotificationByToken(List<NotificationMessage> notificationMessages) throws FirebaseMessagingException {
        List<Message> messages = notificationMessages.stream().map(notificationMessage ->
                Message.builder()
                        .setToken(notificationMessage.recipientToken())
                        .setNotification(Notification.builder()
                                .setTitle(notificationMessage.title())
                                .setBody(notificationMessage.body())
                                .build())
                        .build()
        ).collect(Collectors.toList());

        // Capturing tokens separately to match them with responses
        List<String> tokens = notificationMessages.stream()
                .map(NotificationMessage::recipientToken)
                .toList();

        BatchResponse response = firebaseMessaging.sendAll(messages);

        int successCount = response.getSuccessCount();
        int failureCount = response.getFailureCount();

        if (failureCount == 0) {
        } else {
            List<String> failedTokens = response.getResponses().stream()
                    .filter(r -> !r.isSuccessful())
                    .map(r -> tokens.get(response.getResponses().indexOf(r)))
                    .collect(Collectors.toList());

            throw new RuntimeException("Failed to send notifications to some recipients: " + String.join(", ", failedTokens));
        }
    }
    }

//    public String sendNotificationByToken(NotificationMessage notificationMessage){
//        Notification notification = Notification
//                .builder()
//                .setTitle(notificationMessage.title())
//                .setBody(notificationMessage.body())
//                .build();
//        Message message = Message.builder()
//                .setToken(notificationMessage.recipientToken())
//                .setNotification(notification)
//                .build();
//        try{
//            firebaseMessaging.send(message);
//            return  "Notification Sent";
//        } catch (FirebaseMessagingException e) {
//            throw new RuntimeException(e);
//
//        }
//    }

