package Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassRoomEvent {
    private long classesID;
    private String name ;
    private Long level ;
    private String classOption ;
    private Long SchoolID ;

}
