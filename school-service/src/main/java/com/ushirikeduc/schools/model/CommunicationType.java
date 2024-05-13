package com.ushirikeduc.schools.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunicationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long communiqueTypeID ;
    private String name ;

    @OneToMany(mappedBy = "communicationType", cascade = CascadeType.ALL)
    private List<FieldsProperty> fieldsProperties = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

}
