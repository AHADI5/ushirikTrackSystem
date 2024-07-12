package com.ushirikeduc.disciplineservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeWorkToBeDone {
    @Id
    @GeneratedValue
    private int homeWorkToBeDone ;
    private Date dueDate ;
    private String title ;
    private String status ;

    @ManyToMany(mappedBy = "homeWorkToBeDone")
    List<Discipline> disciplines = new ArrayList<>();

}
