package com.ushirikeduc.maxmanagementservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor

public class ClassWorksAssigned {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long classWorkAssignedID;
    private  long classRoomID ;
    private String classWorkType;
    private String courseName ;
    private int courseID ;
    private  double maxScore  ;
    private  int classWorkID  ;
    private Date gradedDate ;
    boolean isGraded ;

    @OneToMany(mappedBy = "classwork", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Score> scores = new ArrayList<>();



}
