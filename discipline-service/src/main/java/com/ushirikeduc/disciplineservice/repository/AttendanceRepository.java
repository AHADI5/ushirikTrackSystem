package com.ushirikeduc.disciplineservice.repository;

import com.ushirikeduc.disciplineservice.model.Attendance;
import com.ushirikeduc.disciplineservice.model.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance , Integer> {
    List<Attendance> getAttendanceByDiscipline_OwnerID(long owner);


    List<Attendance> findByDateAndDiscipline_ClassRoomID( Date date , long classRoomID );

    List<Attendance> findAttendancesByDate(Date date);
//
//    List<Attendance> findAttendancesByDiscipline_ClassRoomID(long classRoomID);
    Optional<Attendance> findByDateAndDiscipline(Date date, Discipline discipline);
}
