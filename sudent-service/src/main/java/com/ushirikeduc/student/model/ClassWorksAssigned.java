package com.ushirikeduc.student.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ClassWorksAssigned {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long classWorkAssignedID;
    private String title ;
    private String courseName ;
    private int courseID ;
    private  int classWorkID  ;

    @ManyToMany
    @JoinTable(name = "classwork_student",
            joinColumns = @JoinColumn(name = "classwork_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> students = new HashSet<>();
    @OneToMany(mappedBy = "classwork", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Score> scores = new ArrayList<>();


}
