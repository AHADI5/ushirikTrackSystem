package com.ushirikeduc.classservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassInfoResponse  {
    String letter ;
    int level;
    String schoolName;
    int schoolID;
    int classRoomID;
    String optionName ;
}
