package com.ushirikeduc.classservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ClassRoom {
    @Id
    @SequenceGenerator(
            name = "class_id_sequence",
            sequenceName = "class_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "class_id_sequence"
    )
    private long classesID;
    private String name ;
    private Long level ;
//    @OneToOne
//    private Teacher teacher;
    private long schoolID ;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "classroom_option_id")
    private ClassRoomOption classRoomOption;

    @JsonIgnore
    @OneToMany(mappedBy = "studentClass", cascade = CascadeType.ALL)
    private List<Student> students = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "classRoom", cascade = CascadeType.ALL)
    private List<Course> courses = new ArrayList<>();

   @OneToOne
    private PrincipalTeacher principalTeacher;



    //Assign Class to teacher
//    public  void assignTeacher(Teacher teacher) {
//        this.setTeacher(teacher);
//    }
}
