package com.ushirikeduc.courseservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long courseCategoryID  ;
    long schoolID;
    String name ;
    String description  ;
    @OneToMany(mappedBy = "courseCategory" , cascade = CascadeType.ALL)
    private List<Course> courses = new ArrayList<>();

}
