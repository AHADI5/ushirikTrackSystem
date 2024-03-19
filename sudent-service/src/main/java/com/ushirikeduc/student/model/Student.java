package com.ushirikeduc.student.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@Slf4j
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @SequenceGenerator(
            name = "student_id_sequence",
            sequenceName = "student_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "student_id_sequence"
    )
    private int studentID;
    private String name ;
    private String lastName ;
    private String firstName ;
    private int classID ;
    private String gender;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent ;

    @OneToOne(  cascade = CascadeType.ALL)
    private Address address;
}
