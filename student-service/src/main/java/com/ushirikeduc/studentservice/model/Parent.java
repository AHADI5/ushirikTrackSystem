package com.ushirikeduc.studentservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Parent {
    @Id
    @SequenceGenerator(
            name = "student_id_sequence",
            sequenceName = "parent_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "parent_id_sequence"
    )
    private Long id;
    private  String name;
    private  String lastName ;
    private  String email;
    private  String phone ;
//    @OneToMany(mappedBy = "parent")
//    private List<Student> studentList;
}
