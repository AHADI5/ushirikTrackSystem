package com.ushirikeduc.student.request;

import java.util.List;

public record  ParentResponse(
        String name ,
        String lastName  ,
        String email  ,
        String phone  ,
        List<ParentStudentList> students
){

}
