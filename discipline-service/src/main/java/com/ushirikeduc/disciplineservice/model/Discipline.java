package com.ushirikeduc.disciplineservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Discipline {
    @Id
    private  long disciplineID;
    private String owner ;
    private long ownerID ;
    private  String classRoomName ;
    private  long classRoomID ;

    @OneToMany(mappedBy = "student")
    List<Attendance> attendances = new ArrayList<>();
    @OneToMany(mappedBy = "student")
    List<Attendance> incident = new ArrayList<>();





}
