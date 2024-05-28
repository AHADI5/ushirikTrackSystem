package com.ushirikeduc.classservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class PrincipalTeacher extends Teacher {
    @OneToOne(mappedBy = "principalTeacher", cascade = CascadeType.ALL)
    private ClassRoom classRoom;

    // Override builder for Lombok to recognize inherited properties
    @Builder(builderMethodName = "principalTeacherBuilder")
    public PrincipalTeacher(long id, long teacherID, String name, boolean isTitular, List<Course> courses, ClassRoom classRoom) {
        super(id, teacherID, name, isTitular, courses);
        this.classRoom = classRoom;
    }
}