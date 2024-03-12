package com.ushirikeduc.schools.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Classes {
    @Id
    private int classesID;
    private int teacherID;
    private String name ;
    private Long level ;

}
