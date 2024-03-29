package com.ushirikeduc.schools.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @SequenceGenerator(
            name = "address_id_sequence",
            sequenceName = "address_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "address_id_sequence"
    )

    private Integer addressID;
    private String quarter ;
    private String avenue ;
    private String houseNumber ;

}
