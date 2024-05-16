package com.ushirikeduc.schools.requests;

import com.ushirikeduc.schools.model.CommuniqueRecipientType;

import java.util.Date;
import java.util.List;

public record CommuniqueResponse(
        String title,
        String content,
        Date publishedDate,
        long communiqueID,

        CommuniqueRecipientType recipientType,
        List<SimpleRecipient> recipients
) {
}
