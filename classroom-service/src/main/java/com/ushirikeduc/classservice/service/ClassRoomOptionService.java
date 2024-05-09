package com.ushirikeduc.classservice.service;

import com.ushirikeduc.classservice.dto.ClassRoomOptionRequest;
import com.ushirikeduc.classservice.model.ClassRoomOption;
import com.ushirikeduc.classservice.repository.ClassRoomOptionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public record ClassRoomOptionService(
        ClassRoomOptionRepository classRoomOptionRepository
) {

     public ResponseEntity<ClassRoomOption> createClassRoomOption  (int schoolID,ClassRoomOptionRequest request) {
         ClassRoomOption classRoomOption = ClassRoomOption.builder()
                 .optionName(request.name())
                 .schoolID(request.schoolID())
                 .description(request.Description())
                 .schoolID(request.schoolID())
                 .build();
        return ResponseEntity.ok(classRoomOptionRepository.save(classRoomOption));

     }
}
