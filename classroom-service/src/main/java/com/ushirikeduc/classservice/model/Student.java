package com.ushirikeduc.classservice.model;

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

    @ManyToOne
    @JoinColumn(name = "class_id")
    private ClassRoom studentClass;






}
