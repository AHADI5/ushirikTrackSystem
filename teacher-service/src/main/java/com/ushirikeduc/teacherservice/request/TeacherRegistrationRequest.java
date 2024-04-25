package com.ushirikeduc.teacherservice.request;

import com.ushirikeduc.teacherservice.model.Address;

public record TeacherRegistrationRequest(

         String lastName ,
         String firstName ,
         String email,
         int classID ,
         Address address

) {
}
