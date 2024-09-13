package com.ushirikeduc.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecepientPrefs {
    @Id
    private Long prefID;
    private List<Date> notificationSenders = new ArrayList<>();
}
