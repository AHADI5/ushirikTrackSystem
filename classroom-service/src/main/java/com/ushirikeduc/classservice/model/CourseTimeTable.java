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
public class CourseTimeTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseTimeTableId;
    @OneToMany(mappedBy = "courseTimeTable" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<CourseDay> courseDayList  = new ArrayList<>();



}
