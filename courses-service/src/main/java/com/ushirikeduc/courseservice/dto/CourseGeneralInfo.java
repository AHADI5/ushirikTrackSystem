package com.ushirikeduc.courseservice.dto;

import com.ushirikeduc.courseservice.model.ClassWork;
import com.ushirikeduc.courseservice.model.RequiredTool;
import com.ushirikeduc.courseservice.model.Requirement;

import java.util.List;

public record CourseGeneralInfo(
        String name ,
        String description ,
        String category ,
        long courseID ,
        long credit ,
        List<RequiredTool> tools,
        List<Requirement> requirements,
        long assigmentNumber


) {
}
