package com.ushirikeduc.classservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class HomeWorkAssigned {
    @Id
    @SequenceGenerator(
            name = "class_id_sequence",
            sequenceName = "class_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "class_id_sequence"
    )
   int homeWorkAssignedID ;
    String title ;
    Date dateToBeDone  ;
    HomeWorkStatus homeWorkStatus ;
    @ManyToMany(mappedBy = "homeWorks")
    private List<Student> students = new ArrayList<>();

}
