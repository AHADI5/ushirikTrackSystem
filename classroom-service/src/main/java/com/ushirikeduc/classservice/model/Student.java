package com.ushirikeduc.classservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @SequenceGenerator(
            name = "class_id_sequence",
            sequenceName = "class_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "class_id_sequence"
    )
    private long idEnroll;
    private long studentID;
    private Date dateEnrolled;
    private String name ;
    private String gender;
    private String parentEmail ;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "class_id")
    private ClassRoom studentClass;
    @ManyToMany
    @JoinTable(
            name = "eleve_devoir",
            joinColumns = @JoinColumn(name = "eleve_id"),
            inverseJoinColumns = @JoinColumn(name = "devoir_id")
    )
    private List<HomeWorkAssigned> homeWorks = new ArrayList<>();


}
