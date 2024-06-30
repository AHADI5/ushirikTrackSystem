package com.ushirikeduc.classservice.kafka;

import Dto.*;
import com.ushirikeduc.classservice.dto.EventRegisterRequest;
import com.ushirikeduc.classservice.dto.HomeWorkAssignedRegisterRequest;
import com.ushirikeduc.classservice.model.ClassRoom;
import com.ushirikeduc.classservice.model.Teacher;
import com.ushirikeduc.classservice.repository.ClassRoomRepository;
import com.ushirikeduc.classservice.repository.TeacherRepository;
import com.ushirikeduc.classservice.service.ClassRoomEventService;
import com.ushirikeduc.classservice.service.ClassRoomService;
import com.ushirikeduc.classservice.service.CoursesService;
import com.ushirikeduc.classservice.service.HomeWorkServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.kafka.annotation.KafkaListener;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Random;

@Component
@Slf4j
public class KafkaListeners {
    final  ClassRoomService classRoomService ;
    final ClassRoomRepository classRoomRepository;
    final TeacherRepository teacherRepository ;

    final CoursesService coursesService ;

    final ClassRoomEventService classRoomEventService ;
    final HomeWorkServices homeWorkServices ;

    public KafkaListeners(ClassRoomService classRoomService,
                          ClassRoomRepository classRoomRepository,
                          TeacherRepository teacherRepository,
                          CoursesService coursesService, ClassRoomEventService classRoomEventService, HomeWorkServices homeWorkServices) {
        this.classRoomService = classRoomService;
        this.classRoomRepository = classRoomRepository;
        this.teacherRepository = teacherRepository;
        this.coursesService = coursesService;

        this.classRoomEventService = classRoomEventService;
        this.homeWorkServices = homeWorkServices;
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

    /*
    *
    * Consuming classworkEvent
    *
    * */

    @KafkaListener(
            topics = "classwork-created",
            groupId = "classwork-classroom",
            containerFactory = "kafkaListenerContainerFactoryClassWork"

    )

    void listener(ClassWorkEvent classWorkEventEvent) {
        log.info(String.format("ClassWork  Event received in school service => %s",classWorkEventEvent.toString()));

        EventRegisterRequest eventRegisterRequest = new EventRegisterRequest(
                classWorkEventEvent.getTitle(),
               formatDateTime(classWorkEventEvent.getDateToBeDone(),classWorkEventEvent.getStartTime()),
                formatDateTime(classWorkEventEvent.getDateToBeDone(),classWorkEventEvent.getEndTime()),
                "" ,
                classWorkEventEvent.getDescription(),
                generateRandomColor()
        );
        classRoomEventService.registerNewEvent(classWorkEventEvent.getClassID(),  eventRegisterRequest);
    }
    public static String formatDateTime(String dateTimeStr, String localTime) {
        // Define a formatter for the input date string
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

        // Parse the input date string
        LocalDateTime baseDateTime;
        try {
            baseDateTime = LocalDateTime.ofInstant(
                    inputFormatter.parse(dateTimeStr, ZonedDateTime::from).toInstant(),
                    ZoneId.systemDefault()
            );
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateTimeStr, e);
        }

        // Parse the local time string
        LocalTime localTimeParsed = LocalTime.parse(localTime.trim());

        // Combine the date from baseDateTime and time from localTimeParsed
        LocalDateTime combinedDateTime = LocalDateTime.of(baseDateTime.toLocalDate(), localTimeParsed);

        // Create a DateTimeFormatter with the desired format
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

        // Format the combined LocalDateTime object using the DateTimeFormatter
        return combinedDateTime.format(outputFormatter);
    }

    public static String generateRandomColor() {
        // Create a random number generator
        Random random = new Random();

        // Generate random red, green, and blue values (0-255)
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        // Convert each value to a two-digit hexadecimal string with leading zeros
        String hexRed = String.format("%02x", red);
        String hexGreen = String.format("%02x", green);
        String hexBlue = String.format("%02x", blue);

        // Combine the hexadecimal strings with a hash symbol (#)
        return "#" + hexRed + hexGreen + hexBlue;
    }


    @KafkaListener(
            topics = "",
            groupId = "homework-classroom",
            containerFactory = "kafkaListenerContainerFactoryHomeWork"
    )

    void listener(HomeWorkEvent homeWorkEvent) {
        log.info(String.format("HomeWork  Event received in school service => %s",homeWorkEvent.toString()));
        HomeWorkAssignedRegisterRequest request = new HomeWorkAssignedRegisterRequest(
                homeWorkEvent.getTitle(),
                homeWorkEvent.getDateToBeDone(),
                homeWorkEvent.getStudentIDs());
        homeWorkServices.registerNewHomeWork(request);


    }

}
