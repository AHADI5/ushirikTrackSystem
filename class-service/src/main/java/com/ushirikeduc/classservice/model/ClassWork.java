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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassWork {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int classWorkID;
    private String name ;
    private String description ;
    private String type ;
    private int credits;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;


    @ManyToMany
    @JoinTable(name = "classwork_student",
    joinColumns = @JoinColumn(name = "classword_id"),
    inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "classwork" , cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Score> scores = new ArrayList<>();

}
