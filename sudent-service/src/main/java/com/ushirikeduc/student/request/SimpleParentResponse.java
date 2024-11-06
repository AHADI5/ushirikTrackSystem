package com.ushirikeduc.student.request;

public record SimpleParentResponse(
        String name ,
        String lastName  ,
        String email  ,
        String phone  ,
        long parentID
) {
}
