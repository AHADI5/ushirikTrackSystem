package com.ushirikeduc.schools.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    private  String name ;
    private String  email ;
    private  String postalBox;

    @OneToOne
    private Address address;

    @OneToOne
    private Director director;
    @JsonIgnore
    @OneToMany(mappedBy = "school" , cascade = CascadeType.ALL)
    private List<Classes> classes;

    // Other fields and annotations..

}


