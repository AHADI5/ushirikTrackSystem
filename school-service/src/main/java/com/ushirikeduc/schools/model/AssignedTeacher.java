package com.ushirikeduc.schools.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignedTeacher {
    @Id
    @SequenceGenerator(
            name = "teacher_id_sequence",
            sequenceName = "teacher_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.TABLE,
            generator = "teacher_id_sequence"
    )

    private int id;
    private int TeacherID ;
    private  int classID;
    private String name ;

}
