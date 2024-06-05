package com.ushirikeduc.courseservice.service;

import com.ushirikeduc.courseservice.dto.ClassWorkRegistrationResponse;
import com.ushirikeduc.courseservice.dto.Works;
import com.ushirikeduc.courseservice.dto.homeWorkResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public record WorksService(
        HomeWorkService  homeWorkService,
        ClassWorkService classWorkService
) {

    public Works getAllWorks(int classID) {
        List<ClassWorkRegistrationResponse> classWork = classWorkService.getClassWorksByClassRoomID(classID);
        List<homeWorkResponse> homeWorks = homeWorkService.getHomeWorksByClassID(classID);

        return  new Works(
                homeWorks ,
                classWork
        );

    }
}
