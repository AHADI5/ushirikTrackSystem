package com.ushirikeduc.courseservice.dto;

public record CourseCategoryRegisterRequest(
        String name  ,
        String Description  ,
        long schoolID
) {
}
