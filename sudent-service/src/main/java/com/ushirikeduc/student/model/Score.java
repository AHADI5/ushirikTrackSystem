package com.ushirikeduc.student.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "classwork_id")
    private ClassWorksAssigned classwork;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @Column(nullable = false)
    private Double score;


}
