package com.ushirikeduc.maxmanagementservice.service;


import Dto.StudentEvent;
import com.ushirikeduc.maxmanagementservice.model.MaxOwner;
import com.ushirikeduc.maxmanagementservice.repository.MaxOwnerRepository;
import org.springframework.stereotype.Service;

@Service
public record MaxOwnerService(
        MaxOwnerRepository  maxOwnerRepository

) {
    public  void RegisterOwner (StudentEvent studentEvent) {
        MaxOwner maxOwner = MaxOwner.builder()
                .ownerName(studentEvent.getName())
                .studentID(Long.valueOf(studentEvent.getStudentID()))
                .classID((long) studentEvent.getClassID())
                .build();
        maxOwnerRepository.save(maxOwner);
    }
}
