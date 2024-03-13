package com.ushirikeduc.teacherservice.events;

import com.ushirikeduc.teacherservice.model.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherEvent {
    private String message ;
    private Long teacherID ;
    private Long classID ;
    private Teacher teacher;
}
