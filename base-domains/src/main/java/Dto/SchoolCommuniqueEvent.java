package Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolCommuniqueEvent {
    private String sender ;
    private String title ;
    private String content ;
    private List<String> recipients ;

}
