package com.ushirikeduc.classservice.model;

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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class Teacher {
    @Id
    @GeneratedValue
    private long id ;
    private long teacherID ;
    private String name ;
    private  boolean isTitular ;

    @OneToMany(mappedBy = "teacher")
    private List<Course> courses;

}
