package com.ushirikeduc.schools.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchoolYear {
    @Id
    @SequenceGenerator(
            name = "schoolYear_id_sequence",
            sequenceName = "schoolYear_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "schoolYear_id_sequence"
    )
    private  int schoolYearID ;
    Date startingDate ;
    Date endingDate ;
    private  String schoolYear;
    private SchoolYearStatus schoolYearStatus;

    @ManyToOne
    @JoinColumn(name = "school_id")
    School school;
    @OneToMany(mappedBy = "schoolYear" , cascade = CascadeType.ALL)
    private List<Semester> semesters  = new ArrayList<>() ;

}
