package com.ushirikeduc.classservice.dto;

public record AssignPrincipalTeacherRequest (
        long schoolID  ,
        long classID ,
        long teacherID,
        String schoolType
){
}
