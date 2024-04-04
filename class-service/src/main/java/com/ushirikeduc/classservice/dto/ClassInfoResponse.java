package com.ushirikeduc.classservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassInfoResponse  {
    String className ;
    int level;
    String schoolName;
    int schoolID;
}
