package Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectorEvent {
    private String firstName;
    private String lastName ;
    private String password ;
    private String email ;
    private int schoolID ;


}
