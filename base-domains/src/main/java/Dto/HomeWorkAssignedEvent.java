package Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeWorkAssignedEvent {
    int homeWorkID ;
    String title ;
    String sender ;
    String concern ;
    String dueDate  ;
    String status ;
//    int studentID ;
    List<String> recipient ;
    List<Integer> studentIDList ;
}
