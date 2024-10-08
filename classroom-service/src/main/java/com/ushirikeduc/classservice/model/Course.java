package com.ushirikeduc.classservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  int AssignedCourseID ;
    private String name ;
    private String category ;
    private int courseID ;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private ClassRoom classRoom;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    @OneToMany(mappedBy = "course" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<DayPeriods> dayPeriods  = new ArrayList<>();

}
