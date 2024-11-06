package com.ushirikeduc.student.request;

import com.ushirikeduc.student.model.Address;

public record StudentDetailsResponse(
        Integer studentID,
        String name ,
        String lastName ,
        String firstName ,
        String gender ,
        Integer classId,
        SimpleParentResponse simpleParentResponse ,
        Address address
) {
}
