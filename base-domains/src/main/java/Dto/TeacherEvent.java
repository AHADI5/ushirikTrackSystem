package Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherEvent {
    private int teacherID;
    private  int classID;
    private String firstName;
    private String lastName ;
    private String password ;
    private String email ;
}
