package com.ushirikeduc.disciplineservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisciplineCommunique {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long disciplineCommuniqueID ;
    private  String title ;
    @Lob // Annotate with @Lob for long string data types
    @Column(columnDefinition = "TEXT") // Adjust column definition based on your database
    private  String content ;
    private String recipient ;
    private long  classRoomID  ;
    private Date generatedDate ;
}
