package com.ushirikeduc.student.request;

import com.ushirikeduc.student.model.Address;
import com.ushirikeduc.student.model.Parent;

public record StudentRegistrationRequest(
      String name ,
      String lastName ,
      String firstName ,
      String gender ,
      int classID,
      Parent parent,
      Address address
) {
}
