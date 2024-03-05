package com.ushirikeduc.schools.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class School {
    private Integer id ;
    private String name ;
    private String postalBox;
    private String email;

}
