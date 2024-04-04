package com.ushirikeduc.student.request;

public record StudentWithClassResponse(
        Integer studentID,
        String name ,
        String lastName ,

        String firstName ,
        Integer classId,
        String className ,
        Integer classLevel ,
        String schoolName,
        int SchoolID
) {

}
