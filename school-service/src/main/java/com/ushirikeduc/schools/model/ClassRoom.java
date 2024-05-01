package com.ushirikeduc.schools.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassRoom {
    @Id
    @SequenceGenerator(
            name = "rule_id_sequence",
            sequenceName = "rule_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rule_id_sequence"
    )
    private long classRoomID ;
    private  long classID;
    private String name ;
    private Long level ;
    private Long SchoolID ;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(

            name = "classroom_communique",
            joinColumns = @JoinColumn(name = "classRoomID"),
            inverseJoinColumns = @JoinColumn(name = "communiqueID")
    )
    private List<Communique> communiques = new ArrayList<>();



}
