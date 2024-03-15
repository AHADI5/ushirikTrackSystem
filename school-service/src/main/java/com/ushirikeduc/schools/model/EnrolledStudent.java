package com.ushirikeduc.schools.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Optional;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EnrolledStudent {
    @Id
    @SequenceGenerator(
            name = "student_e_id_sequence",
            sequenceName = "student_e_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.TABLE,
            generator = "director_id_sequence"
    )
    private  int id ;
    private int studentID ;
    private String name ;
    private  int classID;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Classes studentClass;


}
