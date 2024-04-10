package com.ushirikeduc.disciplineservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor


public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private  long incidentID ;
    @ManyToOne
    private Rule ruleBypassed ;
    private int occurrenceNumber = 0 ;
    private  String title ;
    private String place ;
    private Date date ;
    private  String description ;
    private String sanction ;

    @ManyToOne
    private Discipline discipline;
}
