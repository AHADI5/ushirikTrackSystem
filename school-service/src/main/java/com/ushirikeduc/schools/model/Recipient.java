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
public class Recipient {
    @Id
    @SequenceGenerator(
            name = "recipient_id_sequence",
            sequenceName = "rule_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "recipient_id_sequence"
    )
    private  long recipientID ;
    private  String recipient ;
    @ManyToMany(mappedBy = "recipientIDs")
    private List<Communique> communiques = new ArrayList<>();

}
