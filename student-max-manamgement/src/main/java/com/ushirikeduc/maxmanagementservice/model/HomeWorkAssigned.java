package com.ushirikeduc.maxmanagementservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeWorkAssigned {
    @Id
    @GeneratedValue
    int homeWorkAssignedID  ;
    String title  ;
    int classRoomID  ;
    int homeworkID  ;
}
