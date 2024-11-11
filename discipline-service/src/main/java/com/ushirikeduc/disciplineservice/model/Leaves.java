package com.ushirikeduc.disciplineservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Leaves {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long leaveID;
    long studentID ; 
    Date createdDate  = new Date();
    Date requestedDateTime = new Date();
    String motif  ;
    Boolean status  ;
}
