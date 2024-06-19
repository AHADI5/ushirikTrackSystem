package Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassWorkEvent {
    private String title ;
    private int courseID ;
    private String courseName ;
    private String description ;
    private String place ;
    private int classID ;
    private int classWorkID ;
    private String classWorkType ;
    private String dateToBeDone ;
    private String startTime ;
    private String endTime ;


}
