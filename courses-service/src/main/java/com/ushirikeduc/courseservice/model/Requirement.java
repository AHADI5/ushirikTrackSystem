package com.ushirikeduc.courseservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Requirement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int requirementID ;
    private String name ;
    private String Description ;
}
