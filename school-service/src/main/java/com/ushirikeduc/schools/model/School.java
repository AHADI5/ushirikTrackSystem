package com.ushirikeduc.schools.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class School {
    @Id
    @SequenceGenerator(
            name = "school_id_sequence",
            sequenceName = "school_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "school_id_sequence"
    )
    private  Integer schoolID;
    private  String schoolName ;
    private  String schoolPostalBox;
    @OneToOne
    private Address address;
    @OneToOne
    private Director director;
    //Address
    //Director

}
