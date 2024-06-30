package Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisciplineEvent {
    String title ;
    String sender ;
    String concern ;
    String content ;
    int id ;
    List<String> recipient ;
}
