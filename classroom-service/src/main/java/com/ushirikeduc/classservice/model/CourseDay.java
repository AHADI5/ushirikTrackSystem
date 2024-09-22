package com.ushirikeduc.classservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CourseDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseDayID;
    private WeekDay weekDay ;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timeTableID")
    private TimeTable timeTable;
    @OneToMany(mappedBy = "courseDay" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<DayPeriods> courseDayList  = new ArrayList<>();


}
