package com.ushirikeduc.student.request;

public record StudentResponse (
        Integer studentID,
        String name ,
        String lastName ,
        String firstName ,
        Integer classId
){
}
