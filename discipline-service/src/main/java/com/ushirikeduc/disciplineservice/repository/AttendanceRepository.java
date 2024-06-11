package com.ushirikeduc.disciplineservice.repository;

import com.ushirikeduc.disciplineservice.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance , Integer> {
    List<Attendance> getAttendanceByDiscipline_OwnerID(long owner);
    List<Attendance> getAttendanceByDisciplineClassRoomIDAndDate(long classRoomID , Date date);
}
