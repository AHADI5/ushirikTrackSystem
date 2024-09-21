package com.ushirikeduc.classservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DayPeriods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dayPeriodID ;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @ManyToOne
    @JoinColumn(name = "courseDayID")
    private CourseDay courseDay  ;
    @ManyToOne
    @JoinColumn(name = "courseID")
    private Course course ;

}
