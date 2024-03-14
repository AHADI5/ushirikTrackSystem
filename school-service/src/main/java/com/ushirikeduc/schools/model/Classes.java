package com.ushirikeduc.schools.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.Optional;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    //Assign Class to teacher
    public  void assignTeacher(Teacher teacher) {
        this.setTeacher(teacher);
    }


}
