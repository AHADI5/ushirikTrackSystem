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
public class CommuniqueReview {
    @Id
    @SequenceGenerator(
            name = "review_id_sequence",
            sequenceName = "communique_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "communique_sequence"
    )
    private  int reviewID  ;
    private String reviewOwner  ;
    private Date  dateReviewed ;
    private boolean reviewStatus  = false ;
    @ManyToOne
    @JoinColumn(name = "communique_id")
    private Communique communique ;
}
