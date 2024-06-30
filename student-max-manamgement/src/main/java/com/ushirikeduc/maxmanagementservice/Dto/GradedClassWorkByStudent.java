package com.ushirikeduc.maxmanagementservice.Dto;

import java.util.Date;

public record GradedClassWorkByStudent(
        String classWorkTitle ,
        int classWorkID  ,
        double score,
        double maxScore  ,
        Date dateGraded ,
        boolean isGraded ,
        String teacherComment
) {
}
