package com.ushirikeduc.schools.service;

import com.ushirikeduc.schools.model.CommunicationType;
import com.ushirikeduc.schools.model.FieldsProperty;
import com.ushirikeduc.schools.model.School;
import com.ushirikeduc.schools.repository.CommuniqueTypeRepository;
import com.ushirikeduc.schools.requests.CommuniqueTypeRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public record CommuniqueTypeService (
        CommuniqueTypeRepository communiqueTypeRepository ,
        SchoolService schoolService
) {

    public  ResponseEntity<CommuniqueTypeRequest> createCommunicationType (long schoolID, CommuniqueTypeRequest communicationType){
        School school = schoolService.getSchool((int) schoolID);
        List<FieldsProperty> fieldsPropertyList = new ArrayList<>();

        for (FieldsProperty fieldsProperty : communicationType.fieldsProperties()){
            FieldsProperty field = FieldsProperty
                    .builder()
                    .fieldType(fieldsProperty.getFieldType())
                    .placeHolder(fieldsProperty.getPlaceHolder())
                    .name(fieldsProperty.getName())
                    .value(fieldsProperty.getValue())
                    .build();
            fieldsPropertyList.add(field);
        }
        CommunicationType communicationType1 = CommunicationType
                .builder()
                .name(communicationType.name())
                .fieldsProperties(fieldsPropertyList)
                .school(school)
                .build();

        CommunicationType savedCommunicationType = communiqueTypeRepository.save(communicationType1);


        return new ResponseEntity<>(new CommuniqueTypeRequest(
                savedCommunicationType.getName(),
                savedCommunicationType.getFieldsProperties()
        ), HttpStatus.CREATED);

    }

    public List<CommunicationType> getSchoolCommunicationTypeList (int schoolID) {
        School school = schoolService.getSchool(schoolID);
        return communiqueTypeRepository.findCommunicationTypesBySchool(school);
    }
}
