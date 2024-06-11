package com.ushirikeduc.disciplineservice.service;

import com.ushirikeduc.disciplineservice.Dto.*;
import com.ushirikeduc.disciplineservice.model.Attendance;
import com.ushirikeduc.disciplineservice.model.Discipline;
import com.ushirikeduc.disciplineservice.repository.AttendanceRepository;
import com.ushirikeduc.disciplineservice.repository.DisciplineRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public record AttendanceService(
        DisciplineRepository disciplineRepository ,
        AttendanceRepository attendanceRepository
) {
    //Record Attendance
    public List<AttendanceResponse> recordAttendance(AttendanceRegisterRequest attendanceRegisterRequestList , int classRoomId ) {
        List<AttendanceResponse>  attendanceResponses = new ArrayList<>() ;
        List<Discipline> disciplines = disciplineRepository.getDisciplineByClassRoomID(classRoomId);
        for (AttendanceSimpleForm attendanceRegisterRequest: attendanceRegisterRequestList.attendanceSimpleFormList()) {
            Discipline discipline = disciplineRepository.getDisciplineByOwnerID(attendanceRegisterRequest.studentID());
            Attendance attendance = Attendance.builder()
                    .discipline(discipline)
                    .isPresent(attendanceRegisterRequest.isPresent())
                    .date(attendanceRegisterRequestList.date())
                    .build();
            attendanceResponses.add(new AttendanceResponse(
                    attendance.getDate() ,
                    attendance.getDiscipline().getOwner(),
                    attendance.isPresent()
            ));
        }
        return attendanceResponses;
    }

    public AttendanceListInClassRoom getAttendancesByDateInClassRoom(AttendanceByDateRequest attendance){
        List<AttendanceResponse> attendanceResponses = new ArrayList<>() ;
        List<Attendance> attendanceList = attendanceRepository.getAttendanceByDisciplineClassRoomIDAndDate(attendance.classRoomId() , attendance.date());


        //If attendance is null set every thing to false , and give back the response

        if(attendanceList.isEmpty()) {
            List<Discipline> disciplines = disciplineRepository.getDisciplineByClassRoomID(attendance.classRoomId());
            for(Discipline discipline : disciplines) {
                AttendanceResponse attendanceResponse = new AttendanceResponse(
                        attendance.date() ,
                        discipline.getOwner(),
                        false
                );
                attendanceResponses.add(attendanceResponse);
            }

           return  new AttendanceListInClassRoom(
                    attendance.date() ,
                    attendanceResponses
            );


        }


        for (Attendance attendanceItem : attendanceList) {
            AttendanceResponse attendanceResponse = new AttendanceResponse(
                    attendanceItem.getDate() ,
                    attendanceItem.getDiscipline().getOwner(),
                    attendanceItem.isPresent()
            );
            attendanceResponses.add(attendanceResponse);
        }
        return  new AttendanceListInClassRoom(
                attendance.date() ,
                attendanceResponses
        );
    }


}
