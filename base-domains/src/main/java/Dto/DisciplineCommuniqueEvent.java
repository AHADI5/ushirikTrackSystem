package Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class DisciplineCommuniqueEvent {
    String title ;
    String dateCreated ;
    String content ;
    String target ;
    long disciplineClassRoomId  ;
}
