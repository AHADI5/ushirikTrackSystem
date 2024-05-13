package com.ushirikeduc.classservice.service;

import com.ushirikeduc.classservice.dto.ClassInfoResponse;
import com.ushirikeduc.classservice.dto.ClassRoomOptionRequest;
import com.ushirikeduc.classservice.dto.ClassRoomOptionResponse;
import com.ushirikeduc.classservice.model.ClassRoomOption;
import com.ushirikeduc.classservice.repository.ClassRoomOptionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public record ClassRoomOptionService(
        ClassRoomOptionRepository classRoomOptionRepository ,
        ClassRoomService classRoomService
) {

     public ResponseEntity<ClassRoomOption> createClassRoomOption  (int schoolID,ClassRoomOptionRequest request) {
         ClassRoomOption classRoomOption = ClassRoomOption.builder()
                 .optionName(request.name())
                 .schoolID(schoolID)
                 .optionName(request.name())
                 .description(request.Description())
                 .schoolID(request.schoolID())
                 .build();
        return ResponseEntity.ok(classRoomOptionRepository.save(classRoomOption));

     }

     public ResponseEntity<List<ClassRoomOptionResponse>> getAllClassSection (int schoolID) {
         List<ClassRoomOption> classRoomOptionList = classRoomOptionRepository.getClassRoomOptionBySchoolID(schoolID);
         List<ClassRoomOptionResponse> classRoomOptionResponses = new ArrayList<>();
         for (ClassRoomOption classRoomOption : classRoomOptionList) {
             ClassRoomOptionResponse classRoomOptionResponse = new ClassRoomOptionResponse(
                     classRoomOption.getClassOptionID(),
                     classRoomOption.getOptionName(),
                     classRoomOption.getDescription() ,
                     classRoomService.classInfoResponseList(classRoomOption.getClassRooms())
//                     classRoomOption.getClassRooms().stream()
//                             .map(classroom -> new ClassInfoResponse(
//                                     classroom.getName(),
//                                     Math.toIntExact(classroom.getLevel()),
//                                     classroom.getName(),
//                                     (int) classroom.getSchoolID(),
//                                     classRoomOption.getOptionName()
//                                     ))
//                             .collect(Collectors.toList())


             );
             classRoomOptionResponses.add(classRoomOptionResponse);
         }

         return ResponseEntity.ok(classRoomOptionResponses);



     }
}
