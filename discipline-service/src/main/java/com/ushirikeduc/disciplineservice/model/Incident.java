package com.ushirikeduc.disciplineservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor


public class Incident {
    @Id
    private  long incidentID ;
    private  String title ;
    private String place ;
    private Date date ;
    private  String description ;
}
