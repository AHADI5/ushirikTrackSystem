package com.ushirikeduc.maxmanagementservice.Dto;

public record StudentResponse (
        Integer studentID,
        String name ,
        String lastName ,
        String firstName ,
        Integer classId
){
}
