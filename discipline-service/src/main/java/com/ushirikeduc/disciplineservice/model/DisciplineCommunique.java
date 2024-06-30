package com.ushirikeduc.disciplineservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisciplineCommunique {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long disciplineCommuniqueID ;
    private  String title ;
    private  String content ;
    private String recipient ;
}
