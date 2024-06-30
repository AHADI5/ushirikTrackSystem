package Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeWorkStatusEvent {
    int homeworkID ;
    String title ;
    String newStatus ;
    String sender ;
    String concern ;
    List<String> recipient ;
}
