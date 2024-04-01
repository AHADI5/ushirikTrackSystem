package com.ushirikeduc.maxmanagementservice.Dto;



public record StudentRegistrationRequest(
      String name ,
      String lastName ,
      String firstName ,
      String gender ,
      int classID

) {
}
