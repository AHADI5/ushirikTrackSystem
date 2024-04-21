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
public class Parent {
    @Id
    @SequenceGenerator(
            name = "parent_id_sequence",
            sequenceName = "address_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "parent_id_sequence"
    )
    private Long parentID;
    private String firstName;
    private String lastName ;
    private String phone ;
    private String email ;
    private int schoolID ;

    @JsonIgnore
    @OneToMany(mappedBy = "parent" ,
            cascade = CascadeType.ALL)
    private List<Student> students;

}
