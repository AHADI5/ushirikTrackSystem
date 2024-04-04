package com.ushirikeduc.student.request;

import lombok.AllArgsConstructor;




public record StudentResponse (
        Integer studentID,
        String name ,
        String lastName ,
        String firstName ,
        Integer classId
){
}
