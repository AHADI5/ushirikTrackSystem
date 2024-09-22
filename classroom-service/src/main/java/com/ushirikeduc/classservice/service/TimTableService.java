package com.ushirikeduc.classservice.service;

import com.ushirikeduc.classservice.dto.TimeTable.CourseDayRequest;
import com.ushirikeduc.classservice.dto.TimeTable.Periods;
import com.ushirikeduc.classservice.dto.TimeTable.TimeTableRequest;
import com.ushirikeduc.classservice.dto.TimeTable.TimeTableResponse;
import com.ushirikeduc.classservice.model.*;
import com.ushirikeduc.classservice.repository.CourseDayRepository;
import com.ushirikeduc.classservice.repository.DayPeriodRepository;
import com.ushirikeduc.classservice.repository.TimeTableRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public record TimTableService(
        ClassRoomService classRoomService  ,
        CoursesService coursesService  ,
        DayPeriodRepository dayPeriodRepository   ,
        CourseDayRepository courseDayRepository ,
        TimeTableRepository timeTableRepository

) {
    public TimeTable registerTimeTable(long classRoomId, TimeTableRequest request) {
        // get classroom by id
        ClassRoom  classRoom = classRoomService.getClassById(classRoomId);

        //Register course day
        List<CourseDay> courseDayList  = new ArrayList<>()  ;
        for(CourseDayRequest courseDayRequest  : request.courseDayList()) {
            List<DayPeriods> dayPeriodsList  = new ArrayList<>() ;
            for(Periods periods : courseDayRequest.periodsList()) {
                Course course  = coursesService.getCourseByID((int) periods.courseID()) ;
                DayPeriods  dayPeriods  = DayPeriods.builder()
                        .course(course)
                        .startTime(periods.startTime())
                        .endTime(periods.endTime())
                        .build() ;
                dayPeriodsList.add(dayPeriodRepository.save(dayPeriods));
            }
            CourseDay courseDay  = CourseDay.builder()
                    .weekDay(courseDayRequest.weekDay())
                    .courseDayList(dayPeriodsList)
                    .build() ;
            courseDayList.add(courseDayRepository.save(courseDay));
            for(DayPeriods dayPeriods : dayPeriodsList) {
                dayPeriods.setCourseDay(courseDay);
                dayPeriodRepository.save(dayPeriods);
            }
        }

        TimeTable timeTable = TimeTable.builder()
                .timeTableCategory(request.timeTableCategory())
                .classRoom(classRoom)
                .courseDayList(courseDayList)
                .build() ;
        TimeTable savedTimeTable = timeTableRepository.save(timeTable);

        //Liking timetable to courseDay
        for(CourseDay courseDay : courseDayList) {
            courseDay.setTimeTable(savedTimeTable);
            courseDayRepository.save(courseDay);
        }





       return savedTimeTable  ;

    }

}
