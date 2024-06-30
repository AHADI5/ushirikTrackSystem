package Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeWorkEvent {
    int HomeWorkID ;
    String title ;
    String Description ;
    String classID ;
    String DateToBeDone;
    List<Integer>  studentIDs ;
}
