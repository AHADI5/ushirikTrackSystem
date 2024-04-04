package com.ushirikeduc.maxmanagementservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaxOwner {

    @Id
    @GeneratedValue
    private Long ownerID ;
    private Long studentID;
    private String ownerName ;
    private Long classID ;
    @ManyToMany
    @JoinTable(
            name = "student_classwork",
            joinColumns = @JoinColumn(name = "maxowner_id"),
            inverseJoinColumns = @JoinColumn(name = "classwork_id")
    )
    private List<ClassWorksAssigned> classwork;


}
