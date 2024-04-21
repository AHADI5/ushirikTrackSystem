package com.ushirikeduc.schools.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
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
            strategy = GenerationType.TABLE,
            generator = "director_id_sequence"
    )

    private Integer directorID;

    private String  firstName ;

    private String lastName ;

    private String directorEmail ;
    private int schoolID ;


    @OneToOne
    private  Address address ;





}
