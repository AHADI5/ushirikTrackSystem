package com.ushirikeduc.courseservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Entity
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClassWork {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int classWorkID ;
    private  ClassworkType classworkType;

    private String description ;
    private int classID ;
    private long maxScore  ;
    private Date createdAt ;
    private Date dateToBeDone ;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

}
