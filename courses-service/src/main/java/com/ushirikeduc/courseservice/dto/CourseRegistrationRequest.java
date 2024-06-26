package com.ushirikeduc.courseservice.dto;

import com.ushirikeduc.courseservice.model.RequiredTool;
import com.ushirikeduc.courseservice.model.Requirement;

import java.util.List;
import java.util.Set;

public record CourseRegistrationRequest(

        String name ,
        String description ,
        int classRoomID ,
        long credits ,
        List<RequiredTool> tools,
        List<Requirement> requirements,
        int courseCategory

) {
}
