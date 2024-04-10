package com.ushirikeduc.courseservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeWorkQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long homeQuestionID ;
    private String question ;
    private String description ;
    private String instruction  ;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course ;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "homework_id")
    private Homework homework;

}
