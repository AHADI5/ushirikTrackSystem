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
    private  long courseID ;
    private String name ;
    private String description ;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "course_tool",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn( name = "tool_id")
    )
    private Set<RequiredTool> tools = new HashSet<>();
    @JsonIgnore
    @OneToMany (cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "couse_id")
    private List<Requirement> requirements = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private ClassRoom classRoom;
}
