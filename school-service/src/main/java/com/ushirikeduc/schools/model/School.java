package com.ushirikeduc.schools.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class School {
    @Id
    @SequenceGenerator(
            name = "school_id_sequence",
            sequenceName = "school_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "school_id_sequence"
    )
    private  long schoolID;
    private  String name ;
    private String  email ;
    private  String postalBox;
    private SchoolType schoolType;

    @OneToOne
    private Address address;

    @OneToOne
    private Director director;
    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    private  List<SchoolEvent> events = new ArrayList<>();

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    private  List<Communique> communiques = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "administrator_id")
    private SchoolAdmin administrator;
    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    private  List<ClassRoom> classRooms = new ArrayList<>();
    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    private  List<CommunicationType> communicationsType = new ArrayList<>();


}


