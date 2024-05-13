package com.ushirikeduc.schools.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldsProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long propertyID ;
    private String name ;
    private String fieldType ;
    private String placeHolder ;
    private String value ;
    @ManyToOne
    @JoinColumn(name = "communiquetype_id")
    private CommunicationType communicationType ;
}