package com.ushirikeduc.classservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor

public  class Teacher {
    @Id
    @GeneratedValue
    private long id ;
    private long teacherID ;
    private String name ;
    private String email;
    private boolean isTitular ;
    @OneToMany(mappedBy = "teacher" , cascade = CascadeType.ALL)
    List<Course> courses ;
    @JsonIgnore
    @OneToOne(mappedBy = "principalTeacher")
    ClassRoom classRoom;


}
