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
public class Semester {
    @Id
    @SequenceGenerator(
            name = "semster_id_sequence",
            sequenceName = "schoolYear_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "smester_id_sequence"
    )
    private  int semesterID ;
    Date startingDate ;
    Date endingDate ;
    @OneToMany(mappedBy = "semester", cascade = CascadeType.ALL)
    List<SemesterPeriod> semesterPeriodList = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "schoolyear_ID")
    SchoolYear schoolYear ;

}
