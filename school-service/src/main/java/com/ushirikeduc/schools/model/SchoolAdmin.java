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
    @GeneratedValue
    private  int schoolAdminID ;
    String firstName;
    String lastName;
    String password;
    String email;
    @OneToMany(mappedBy = "administrator", cascade = CascadeType.ALL)
    private List<School> schools;


}
