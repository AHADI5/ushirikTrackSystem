package com.ushirikeduc.schools.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Communique {
    @Id
    @SequenceGenerator(
            name = "rule_id_sequence",
            sequenceName = "rule_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rule_id_sequence"
    )
    private long communiqueID ;
    private String title;
    private String content ;
    private Date date;
    private CommuniqueType communiqueType ;
    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

}
