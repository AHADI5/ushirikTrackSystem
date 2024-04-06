package com.ushirikeduc.schools.service;

import com.ushirikeduc.schools.model.School;
import com.ushirikeduc.schools.model.SchoolEvent;
import com.ushirikeduc.schools.repository.EventRepository;
import com.ushirikeduc.schools.repository.SchoolRepository;
import com.ushirikeduc.schools.requests.EventRegisterRequest;
import com.ushirikeduc.schools.requests.EventResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public record EventService (
        SchoolRepository schoolRepository ,
        EventRepository eventRepository ,
        SchoolService service
) {

    public EventResponse registerNewEvent (int schoolID , EventRegisterRequest request) {

        Date startingDate = parseDate(request.startingDate());
        Date endingDate = parseDate(request.endingDate());
        School school =service.getSchool(schoolID);

        SchoolEvent schoolEvent = SchoolEvent.builder()
                .title(request.title())
                .startingDate(startingDate)
                .school(school)
                .endingDate(endingDate)
                .description(request.description())
                .build();
        SchoolEvent savedSchoolEvent =eventRepository.save(schoolEvent);
        return  new EventResponse(
                savedSchoolEvent.getTitle(),
                savedSchoolEvent.getDescription(),
                savedSchoolEvent.getStartingDate(),
                savedSchoolEvent.getEndingDate()
        );

    }

    public List<EventResponse> getSchoolEvents (int schoolID) {
        School school = service.getSchool(schoolID) ;

        List<EventResponse> eventResponses = new ArrayList<>();

        List<SchoolEvent> schoolEvents = school.getEvents();
        for (SchoolEvent event : schoolEvents) {
            EventResponse eventResponse = new EventResponse(
                    event.getTitle(),
                    event.getDescription(),
                    event.getStartingDate(),
                    event.getEndingDate()
            );

            eventResponses.add(eventResponse);
        }

        return eventResponses;
    }

    public List<EventResponse> getUpcomingEvents (int schoolID) {
        //Getting the school
        School school = service.getSchool(schoolID);

        //Getting the current Date

        Date currentDate = new Date();

        //initialize a list to store all upcoming Event
        List<EventResponse> upComingEventsResponse = new ArrayList<>();

        //Query the database for events with start time grater than the current time

        List<SchoolEvent> upComingEvents  =
                eventRepository.findSchoolEventByStartingDateAfterAndSchool(currentDate,school);

        for (SchoolEvent events : upComingEvents){
            EventResponse upComingEvent = new EventResponse(
                    events.getTitle(),
                    events.getDescription(),
                    events.getStartingDate(),
                    events.getEndingDate()
            );
            upComingEventsResponse.add(upComingEvent);
        }

        return upComingEventsResponse;

    }

//    public List<EventResponse> registerEventList(int schoolID ,
//                                                 List<EventRegisterRequest> requests) {
//
//
//    }

    public Date  parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateString) ;
        } catch (ParseException e) {
            throw new IllegalStateException("Invalid date format ");
        }

    }
}
