package com.ushirikeduc.schools.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SemesterPeriod {
    @Id
    @SequenceGenerator(
            name = "semster_id_sequence",
            sequenceName = "schoolYear_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "smester_id_sequence"
    )
    private int semesterPeriodID ;
    Date startingDate ;
    Date endingDate ;
    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester ;

}
