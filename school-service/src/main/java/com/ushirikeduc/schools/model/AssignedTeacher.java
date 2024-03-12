package com.ushirikeduc.schools.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignedTeacher {
    @Id
    private int id;
    private int TeacherID ;
    private String name ;

}
