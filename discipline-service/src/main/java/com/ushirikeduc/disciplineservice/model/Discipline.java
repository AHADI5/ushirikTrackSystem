package com.ushirikeduc.disciplineservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private  long disciplineID;
    private String owner ;
    private long ownerID ;
    private  String classRoomName ;
    private  long classRoomID ;
    private  String parentEmail ;

    @OneToMany(mappedBy = "discipline")
    List<Attendance> attendances = new ArrayList<>();
    @OneToMany(mappedBy = "discipline")
    List<Incident> incident = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "discipline_homework",
            joinColumns = @JoinColumn(name = "discipline_id"),
            inverseJoinColumns = @JoinColumn(name = "homework_id")
    )
    List<HomeWorkToBeDone> homeWorkToBeDone = new ArrayList<>();



}
