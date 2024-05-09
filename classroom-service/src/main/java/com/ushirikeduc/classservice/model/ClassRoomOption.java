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
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassRoomOption {
    @Id
    @SequenceGenerator(
            name = "class_option_sequence",
            sequenceName = "class_option_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "class_option_sequence"
    )
    long classOptionID ;
    long schoolID;
    String optionName ;
    String description ;

    @JsonIgnore
    @OneToMany(mappedBy = "classRoomOption", cascade = CascadeType.ALL)
    private List<ClassRoom> classRooms = new ArrayList<>();


}
