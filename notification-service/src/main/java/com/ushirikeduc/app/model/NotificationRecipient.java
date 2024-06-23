package com.ushirikeduc.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRecipient {

    @Id
    @SequenceGenerator(
            name = "recipient_id_sequence",
            sequenceName = "recipient_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "recipient_id_sequence"
    )
    private int recipientID;

    private String email;
    private String uniqueDeviceKey;

    @ManyToMany
    @JoinTable( // Define the join table for the many-to-many relationship
            name = "notification_recipients_messages", // Join table name
            joinColumns = @JoinColumn(name = "recipient_id", referencedColumnName = "recipientID"),
            inverseJoinColumns = @JoinColumn(name = "notification_id", referencedColumnName = "notificationID")
    )
    private List<NotificationMessageSent> notificationMessages = new ArrayList<>();

    // Getters and setters (omitted for brevity)
}

