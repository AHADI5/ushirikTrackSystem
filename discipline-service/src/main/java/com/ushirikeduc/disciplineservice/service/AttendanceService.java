package com.ushirikeduc.disciplineservice.service;

import com.ushirikeduc.disciplineservice.Dto.AttendanceRegisterRequest;
import com.ushirikeduc.disciplineservice.Dto.AttendanceResponse;
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
    public AttendanceResponse recordAttendance(int studentID, AttendanceRegisterRequest request) {
        Discipline discipline = disciplineRepository.getDisciplineByOwnerID(studentID);


        Attendance attendance = Attendance.builder()
                .date(new Date())
                .discipline(discipline)
                .isPresent(request.isPresent())
                .build();
       Attendance savedAttendance = attendanceRepository.save(attendance);


        return new AttendanceResponse(
                savedAttendance.getDate(),
                savedAttendance.isPresent()

        );
    }

    public List<AttendanceResponse> getAttendanceList(Long studentID){
        List<Attendance> attendances = attendanceRepository.getAttendanceByDiscipline_OwnerID(studentID);

        //this list will store , attendances in more flexible format

        List<AttendanceResponse> attendanceResponses = new ArrayList<>();

        for (Attendance attendance : attendances ) {
            AttendanceResponse attendanceResponse = new AttendanceResponse(
                    attendance.getDate(),
                    attendance.isPresent()
            );
            attendanceResponses.add(attendanceResponse);
        }


        return  attendanceResponses;
    }
}
