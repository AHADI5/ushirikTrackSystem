package com.ushirikeduc.schools.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Communique {
    @Id
    @SequenceGenerator(
            name = "communique_id_sequence",
            sequenceName = "communique_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "communique_id_sequence"
    )
    private long communiqueID ;
    private String title;
    @Lob // Annotate with @Lob for long string data types
    @Column(columnDefinition = "TEXT") // Adjust column definition based on your database
    private String content ;
    private Date dateCreated;
    private CommuniqueCategory category;


    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;
    private CommuniqueRecipientType recipientType;
    private boolean isReviewed  ;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "communique_recipients",
            joinColumns = @JoinColumn(name = "communique_id"),
            inverseJoinColumns = @JoinColumn(name = "recipient_id"))
    private List<Recipient> recipientIDs = new ArrayList<>();
    @OneToMany(mappedBy = "communique", cascade = CascadeType.ALL)
    private  List<CommuniqueReview> reviews = new ArrayList<>();

}
