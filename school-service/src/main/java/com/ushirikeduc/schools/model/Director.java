package com.ushirikeduc.schools.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Director {
    @Id
    @SequenceGenerator(
            name = "director_id_sequence",
            sequenceName = "director_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "director_id_sequence"
    )

    private Integer directorID;
    private String  name ;
    private String middleName ;
    private String firstName ;
    private String gender ;

}
