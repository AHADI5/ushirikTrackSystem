package com.ushirikeduc.users.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Parent {
    @Id
    Integer parentUserID ;
    String userName ;
    String password ;
    String email ;

}
