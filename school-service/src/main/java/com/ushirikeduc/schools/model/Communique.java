package com.ushirikeduc.schools.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.naming.CommunicationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    private Date dateCreated;
//    private List<Integer> reach = new ArrayList<>() ;

//    private CommuniqueType communiqueType ;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private CommunicationType type;
    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;
    private CommuniqueRecipientType recipientType;
    @ManyToMany
    @JoinTable(
            name = "recipients",
            joinColumns = @JoinColumn(name = "communique_id"),
            inverseJoinColumns = @JoinColumn(name = "recipient_id"))
    private List<Recepient> recipientIDs ;




}
