package com.ushirikeduc.schools.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class School {
    @Id
    @SequenceGenerator(
            name = "school_id_sequence",
            sequenceName = "school_id_sequence"
    )
    @GeneratedValue (
            strategy = GenerationType.SEQUENCE,
            generator = "school_id_sequence"
    )
    private Integer id ;
    private String name ;
    private String postalBox;
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id",referencedColumnName ="idAddress")
    private Address address;

}
