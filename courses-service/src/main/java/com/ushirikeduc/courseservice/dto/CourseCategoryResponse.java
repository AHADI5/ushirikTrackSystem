package com.ushirikeduc.courseservice.dto;

import java.util.List;

public record CourseCategoryResponse(
        String name ,
        String description  ,

        List<CourseInfo> courses ,
        long courseCategory
) {

}
