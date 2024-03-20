package com.ushirikeduc.classservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long scoreID ;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    private double score;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "classwork_id", nullable = false)
    private ClassWork classwork;
}
