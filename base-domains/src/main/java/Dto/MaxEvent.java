package Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaxEvent {
    int maxID ;
    String title ;
    double max;
    String sender ;
    String concern ;
    List<String> recipient ;

}
