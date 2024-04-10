package com.ushirikeduc.disciplineservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViolationType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int violationID ;
    private  int occurrence ;
    private  String sanction ;

    @ManyToOne
    private  Rule rule ;
}
