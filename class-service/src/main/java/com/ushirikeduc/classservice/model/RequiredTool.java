package com.ushirikeduc.classservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequiredTool {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long toolID ;
    private String name ;
}
