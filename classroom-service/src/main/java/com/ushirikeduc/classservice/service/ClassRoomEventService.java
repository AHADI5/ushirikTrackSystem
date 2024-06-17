package com.ushirikeduc.classservice.service;

import com.ushirikeduc.classservice.dto.ClassRoomEventResponse;
import com.ushirikeduc.classservice.dto.EventRegisterRequest;
import com.ushirikeduc.classservice.model.ClassRoom;
import com.ushirikeduc.classservice.model.ClassRoomEvent;
import com.ushirikeduc.classservice.repository.ClassRoomEventRepository;
import com.ushirikeduc.classservice.repository.ClassRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public record ClassRoomEventService(
        ClassRoomEventRepository classRoomEventRepository ,
        ClassRoomRepository classRoomRepository,
        ClassRoomService classRoomService
) {
    public ClassRoomEventResponse registerNewEvent(int classID, EventRegisterRequest request) {
        Date startingDate = parseDate(request.startingDate());
        Date endingDate = parseDate(request.endingDate());
        ClassRoom classRoom = classRoomService.getClassById((long) classID);
        ClassRoomEvent classRoomEvent = ClassRoomEvent.builder()
                .title(request.title())
                .startingDate(startingDate)
                .classRoom(classRoom)
                .endingDate(endingDate)
                .place(request.place())
                .description(request.description())
                .color(request.color())
                .build();
        ClassRoomEvent savedClassRoomEvent = classRoomEventRepository.save(classRoomEvent);
        return  new ClassRoomEventResponse(
                savedClassRoomEvent.getTitle(),
                savedClassRoomEvent.getDescription(),
                savedClassRoomEvent.getPlace(),
                savedClassRoomEvent.getStartingDate(),
                savedClassRoomEvent.getEndingDate(),
                savedClassRoomEvent.getColor(),
                savedClassRoomEvent.getClassRoomEventID()
        );
    }

    public List<ClassRoomEventResponse> getClassRoomByClassRoomID(int classID) {
        ClassRoom classRoom = classRoomService.getClassById((long) classID);
        List<ClassRoomEvent> classRoomEvents = classRoom.getClassRoomEvents();
        List<ClassRoomEventResponse> classRoomEventResponses = new ArrayList<>();

        for (ClassRoomEvent classRoomEvent : classRoomEvents) {
            ClassRoomEventResponse classRoomEventResponse = new ClassRoomEventResponse(
                    classRoomEvent.getTitle(),
                    classRoomEvent.getDescription(),
                    classRoomEvent.getPlace(),
                    classRoomEvent.getStartingDate() ,
                    classRoomEvent.getEndingDate(),
                    classRoomEvent.getColor(),
                    classRoomEvent.getClassRoomEventID()
            );
            classRoomEventResponses.add(classRoomEventResponse);

        }
        return  classRoomEventResponses;


    }

    public Date  parseDate(String dateString) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateString) ;
        } catch (ParseException e) {
            throw new IllegalStateException("Invalid date format ");
        }

    }
}
