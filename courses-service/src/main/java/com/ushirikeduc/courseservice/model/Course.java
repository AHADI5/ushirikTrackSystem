package com.ushirikeduc.courseservice.model;

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
    private  int courseID ;
    private String name ;
    private String description ;
    private int classRoomID ;
    private  int credits ;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "course_tool",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn( name = "tool_id")
    )
    private List<RequiredTool> tools = new ArrayList<>();
    @JsonIgnore
    @OneToMany (cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "couse_id")
    private List<Requirement> requirements = new ArrayList<>();

    @JsonIgnore
    @OneToMany (mappedBy = "course" ,cascade = CascadeType.ALL)
    private List<ClassWork>  classWorks = new ArrayList<>();

    @OneToMany(mappedBy = "course" , cascade = CascadeType.ALL)
    private  List<HomeWorkQuestion> questions = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "course_category")
    private CourseCategory courseCategory ;
}
