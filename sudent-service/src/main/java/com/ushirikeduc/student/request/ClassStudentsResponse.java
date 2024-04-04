package com.ushirikeduc.student.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClassStudentsResponse {
    String className ;
    int level;
    String schoolName;
    int schoolID;
}
