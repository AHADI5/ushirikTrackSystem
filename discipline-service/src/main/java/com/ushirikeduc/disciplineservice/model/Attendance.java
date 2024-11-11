package com.ushirikeduc.disciplineservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long attendanceID;
    private Date date ;
    private boolean isPresent;
    AttendanceStatus attendanceStatus ;
    @ManyToOne
    private Discipline discipline;

}
