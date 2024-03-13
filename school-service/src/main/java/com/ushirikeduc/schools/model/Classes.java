package com.ushirikeduc.schools.model;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Classes {
    @Id
    @SequenceGenerator(
            name = "class_id_sequence",
            sequenceName = "class_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.TABLE,
            generator = "class_id_sequence"
    )


    private int classesID;
    private String name ;
    private Long level ;
    @OneToOne
    private AssignedTeacher teacher;

}
