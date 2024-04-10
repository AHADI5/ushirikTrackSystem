package com.ushirikeduc.disciplineservice.model;

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
public class Rule {
    @Id
    @SequenceGenerator(
            name = "rule_id_sequence",
            sequenceName = "rule_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rule_id_sequence"
    )
    private Integer ruleId ;
    private  Integer schoolID;
    private String title ;
    private String content ;
    @OneToMany(mappedBy = "rule", cascade = CascadeType.ALL)
    private List<ViolationType> violationList = new ArrayList<>();

}
