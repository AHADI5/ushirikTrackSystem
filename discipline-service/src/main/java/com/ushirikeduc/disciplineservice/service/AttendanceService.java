package com.ushirikeduc.disciplineservice.service;

import Dto.DisciplineEvent;
import com.ushirikeduc.disciplineservice.Dto.*;
import com.ushirikeduc.disciplineservice.controller.MessageController;
import com.ushirikeduc.disciplineservice.model.Attendance;
import com.ushirikeduc.disciplineservice.model.Discipline;
import com.ushirikeduc.disciplineservice.repository.AttendanceRepository;
import com.ushirikeduc.disciplineservice.repository.DisciplineRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public record AttendanceService(
        DisciplineRepository disciplineRepository ,
        AttendanceRepository attendanceRepository,
        MessageController messageController
) {
    //Record Attendance
    public List<AttendanceResponse> recordAttendance(AttendanceRegisterRequest attendanceRegisterRequestList, int classRoomId) {
        List<AttendanceResponse> attendanceResponses = new ArrayList<>();
        List<Discipline> disciplines = disciplineRepository.getDisciplineByClassRoomID(classRoomId);

        for (AttendanceSimpleForm attendanceRegisterRequest : attendanceRegisterRequestList.attendances()) {
            Discipline discipline = disciplineRepository.getDisciplineByOwnerID(attendanceRegisterRequest.studentID());

            // Check if the attendance already exists
            Optional<Attendance> existingAttendanceOpt = attendanceRepository.findByDateAndDiscipline(
                    attendanceRegisterRequestList.date(), discipline);

            Attendance attendance;
            if (existingAttendanceOpt.isPresent()) {
                // Update existing attendance
                attendance = existingAttendanceOpt.get();
                attendance.setPresent(attendanceRegisterRequest.isPresent());
            } else {
                // Create new attendance
                attendance = Attendance.builder()
                        .discipline(discipline)
                        .isPresent(attendanceRegisterRequest.isPresent())
                        .date(attendanceRegisterRequestList.date())
                        .build();
            }

            // Save attendance (either new or updated)
            Attendance savedAttendance = attendanceRepository.save(attendance);
            if (!attendance.isPresent())
                messageController.publishDiscipline(createAttendanceEvent(savedAttendance));
            attendanceResponses.add(new AttendanceResponse(
                    savedAttendance.getDate(),
                    (int) savedAttendance.getDiscipline().getOwnerID(),
                    savedAttendance.getDiscipline().getOwner(),
                    savedAttendance.isPresent()
            ));
        }

        return attendanceResponses;
    }


    public AttendanceListInClassRoom getAttendancesByDateInClassRoom(AttendanceByDateRequest attendance){
        List<AttendanceResponse> attendanceResponses = new ArrayList<>() ;
        List<Attendance> attendanceList = attendanceRepository.findAttendancesByDate(attendance.date());
//        List<Attendance> attendanceList = attendanceRepository.findByDateAndDiscipline_ClassRoomID( attendance.date(),attendance.classRoomId() );

//        log.info(attendanceList.toString());

        //If attendance is null set every thing to false , and give back the response

        if(attendanceList.isEmpty()) {
            List<Discipline> disciplines = disciplineRepository.getDisciplineByClassRoomID(attendance.classRoomId());
            for(Discipline discipline : disciplines) {
                AttendanceResponse attendanceResponse = new AttendanceResponse(
                        attendance.date() ,
                        (int) discipline.getOwnerID() ,
                        discipline.getOwner() ,
                        false
                );
                attendanceResponses.add(attendanceResponse);
            }

        } else {
            for (Attendance attendanceItem : attendanceList) {
                AttendanceResponse attendanceResponse = new AttendanceResponse(
                        attendanceItem.getDate() ,
                        (int) attendanceItem.getDiscipline().getOwnerID(),
                        attendanceItem.getDiscipline().getOwner(),
                        attendanceItem.isPresent()
                );
                attendanceResponses.add(attendanceResponse);
            }
        }
        return  new AttendanceListInClassRoom(
                 attendance.date() ,
                 attendanceResponses
         );
    }


    public DisciplineEvent createAttendanceEvent(Attendance attendance) {
        List<String> emails = new ArrayList<>();
        DisciplineEvent disciplineEvent = new DisciplineEvent();
        disciplineEvent.setTitle("Absence");
        disciplineEvent.setContent("L'enfant n'est pas venue à l'école aujourd'hui");
       disciplineEvent.setSender("Discipline");
       disciplineEvent.setConcern("Attendance");
       emails.add(attendance.getDiscipline().getParentEmail());
       disciplineEvent.setRecipient(emails);
       disciplineEvent.setId(Math.toIntExact(attendance.getAttendanceID()));

        return  disciplineEvent ;

    }

}
