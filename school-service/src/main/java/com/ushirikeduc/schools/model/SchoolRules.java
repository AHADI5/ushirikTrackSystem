package com.ushirikeduc.schools.model;

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
@AllArgsConstructor
@NoArgsConstructor
public class SchoolRules {

    @Id
    @SequenceGenerator(
            name = "rule_id_sequence",
            sequenceName = "rule_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rule_id_sequence"
    )

    private Integer Id ;
    private Integer ruleId ;
    @ManyToOne()
    @JoinColumn(name = "school_id")
    private School school;


}
