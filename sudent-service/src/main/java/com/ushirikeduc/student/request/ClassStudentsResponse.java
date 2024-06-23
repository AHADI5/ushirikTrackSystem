package com.ushirikeduc.student.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClassStudentsResponse {
    String letter ;
    int level;
    String schoolName;
    int schoolID;
    int classRoomID;
    String optionName ;
}
