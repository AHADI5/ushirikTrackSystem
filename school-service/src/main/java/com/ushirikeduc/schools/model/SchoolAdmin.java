package com.ushirikeduc.schools.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchoolAdmin {
    @Id
    @SequenceGenerator(
            name = "school_id_sequence",
            sequenceName = "admin_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "admin_id_sequence"
    )
    private  long schoolAdminID ;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    @OneToMany(mappedBy = "administrator", cascade = CascadeType.ALL)
    private List<School> schools;


}
