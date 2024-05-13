package com.ushirikeduc.schools.requests;

import com.ushirikeduc.schools.model.FieldsProperty;

import java.util.List;
import java.util.Map;

public record CommuniqueTypeRequest(
         String name ,
         List<FieldsProperty> fieldsProperties
) {
}
