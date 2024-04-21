package com.ushirikeduc.teacherservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    @Id
    @SequenceGenerator(
            name = "teacher_id_sequence",
            sequenceName = "teacher_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "teacher_id_sequence"
    )
    private Long id;
    private int classID;
    private int schoolID ;
    private String firstName;
    private String lastName ;
    private String email ;
    @OneToOne
    private Address address;

}
