package com.ushirikeduc.app.Dto;

import java.util.List;
import java.util.Map;

public record NotificationDto(
        String title,
        String content,
        Map<String, String> data ,// Map to represent additional data

        List<String> emails
) {
}

