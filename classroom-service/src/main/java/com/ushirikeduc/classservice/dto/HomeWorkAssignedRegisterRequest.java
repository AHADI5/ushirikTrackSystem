package com.ushirikeduc.classservice.dto;

import java.util.List;

public record HomeWorkAssignedRegisterRequest(
        String title  ,
        String dateToBeDone ,
        List<Integer> studentIDs
) {

}
