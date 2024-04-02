package Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassWorkEvent {
    private String title ;
    private int courseID ;
    private String courseName ;
    private int classWorkID ;
    private String classWorkType ;
}
