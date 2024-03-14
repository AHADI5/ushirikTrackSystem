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
            strategy = GenerationType.SEQUENCE,
            generator = "class_id_sequence"
    )
    private long classesID;
    private String name ;
    private Long level ;
    @OneToOne
    private Teacher teacher;

    //Assign Class to teacher
    public  void assignTeacher(Teacher teacher) {
        this.setTeacher(teacher);
    }

}
