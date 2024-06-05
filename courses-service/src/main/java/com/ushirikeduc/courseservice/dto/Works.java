package com.ushirikeduc.courseservice.dto;

import java.util.List;

public record Works(
        List<homeWorkResponse> homeWorks,
        List<ClassWorkRegistrationResponse> classWorks ) {

}
