package com.ushirikeduc.app.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;



public record NotificationMessage (
        int notificationID,
        String recipientToken ,
         String title  ,
         String body ,
         Map<String , String> data
){


}
