package com.ushirikeduc.courseservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Homework {
    @Id
    @GeneratedValue
    private  long homeWorkID ;
    private String title ;
    private String description ;
    private int classRoomID ;
    private Date creationDate ;
    private Date dateToBeDone  ;

    @OneToMany(mappedBy = "homework" , cascade = CascadeType.ALL)
    private List<HomeWorkQuestion> questions = new ArrayList<>();

    // set the creation date to the current date
}
