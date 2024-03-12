package com.ushirikeduc.schools.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.*;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Classes {
    @Id
    private int classesID;
    private String name ;
    private Long level ;
    @OneToOne
    private AssignedTeacher teacher;

}
