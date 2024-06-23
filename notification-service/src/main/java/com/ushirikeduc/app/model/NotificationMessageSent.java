package com.ushirikeduc.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Slf4j
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class NotificationMessageSent {

    @Id
    @SequenceGenerator(
            name = "address_id_sequence",
            sequenceName = "address_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "address_id_sequence"
    )
    private int notificationID;
    private Date date ;
    private String title;
    private String content;

    @ManyToMany(mappedBy = "notificationMessages") // Map the relationship from NotificationRecipient side
    private List<NotificationRecipient> recipients = new ArrayList<>();

}
