package Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassWorkEvent {
    private String title ;
    private int courseID ;
    private String courseName ;
    private int classWorkID ;


}
