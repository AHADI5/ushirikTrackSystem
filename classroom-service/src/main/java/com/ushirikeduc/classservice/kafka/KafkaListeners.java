package com.ushirikeduc.classservice.kafka;

import Dto.CourseEvent;
import Dto.StudentEvent;
import Dto.TeacherEvent;
import com.ushirikeduc.classservice.model.Teacher;
import com.ushirikeduc.classservice.repository.ClassRoomRepository;
import com.ushirikeduc.classservice.repository.TeacherRepository;
import com.ushirikeduc.classservice.service.ClassRoomService;
import com.ushirikeduc.classservice.service.CoursesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.kafka.annotation.KafkaListener;

@Component
@Slf4j
public class KafkaListeners {
    final  ClassRoomService classRoomService ;
    final ClassRoomRepository classRoomRepository;
    final TeacherRepository teacherRepository ;

    final CoursesService coursesService ;

    public KafkaListeners(ClassRoomService classRoomService,
                          ClassRoomRepository classRoomRepository,
                          TeacherRepository teacherRepository,
                          CoursesService coursesService) {
        this.classRoomService = classRoomService;
        this.classRoomRepository = classRoomRepository;
        this.teacherRepository = teacherRepository;
        this.coursesService = coursesService;

    }

    /*
    * Student Listener
    * */
    @KafkaListener(
            topics = "student-created",
            groupId = "student",
            containerFactory = "kafkaListenerContainerFactoryStudent"

    )

    void listener(StudentEvent studentEvent) {
        log.info("Event received in ClassRoom  service %s" + studentEvent.toString() );
        classRoomService.createStudent(studentEvent);
    }

    /*
     * Teacher Listener
     *
     */
    @KafkaListener(
            topics = "teacher-created",
            groupId = "teacher-classroom",
            containerFactory = "kafkaListenerContainerFactoryTeacher"

    )
    void listener(TeacherEvent teacherEvent) {
        log.info(String.format("Teacher  Event received in school service => %s",teacherEvent.toString()));
        // save the teacher in the database

        Teacher teacher = Teacher.builder()
//                .classID((long) teacherEvent.getClassID())
                .teacherID((long) teacherEvent.getTeacherID())
                .schoolType(teacherEvent.getSchoolType())
                .schoolID(teacherEvent.getSchoolID())
                .isTitular(false)
                .email(teacherEvent.getEmail())
                .name(teacherEvent.getFirstName() + " " + teacherEvent.getLastName())
                .build();
        Teacher savedTeacher = teacherRepository.save(teacher);
        log.info("Class Assigned Successfully" + savedTeacher);
    }

    /***
     * Consuming courseEvent
     */
    @KafkaListener(
            topics = "course-created",
            groupId = "course-classroom",
            containerFactory = "kafkaListenerContainerFactoryCourse"

    )

    void listener(CourseEvent courseEvent) {
        log.info(String.format("Teacher  Event received in school service => %s",courseEvent.toString()));
        coursesService.registerCourse(courseEvent);
    }

}
