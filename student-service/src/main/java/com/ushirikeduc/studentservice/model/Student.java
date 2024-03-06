package com.ushirikeduc.studentservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
    @Id
    @SequenceGenerator(
            name = "student_id_sequence",
            sequenceName = "school_id_sequence"
    )
    @GeneratedValue (
            strategy = GenerationType.SEQUENCE,
            generator = "student_id_sequence"
    )

    private Long id;
    private String name ;
    private String lastName ;
    private String firstName;
   @ManyToOne
   @JoinColumn(name = "parent_id")
    private Parent parent ;



}
