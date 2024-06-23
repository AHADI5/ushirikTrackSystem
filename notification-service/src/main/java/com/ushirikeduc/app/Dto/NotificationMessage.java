package com.ushirikeduc.app.Dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;



public record NotificationMessage (

        String recipientToken ,
         String recipientID ,
         String title  ,
         String body

){


}
