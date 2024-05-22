package com.ushirikeduc.classservice.service;

import Dto.ClassRoomEvent;
import Dto.StudentEvent;
import com.ushirikeduc.classservice.controller.MessageController;
import com.ushirikeduc.classservice.dto.*;
import com.ushirikeduc.classservice.model.*;
import com.ushirikeduc.classservice.repository.ClassRoomOptionRepository;
import com.ushirikeduc.classservice.repository.ClassRoomRepository;
import com.ushirikeduc.classservice.repository.EnrolledStudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ClassRoomService{
    final ClassRoomRepository classRepository;

    final EnrolledStudentRepository enrolledStudentRepository;
    final MessageController messageController ;
    final ClassRoomOptionRepository classRoomOptionRepository;

//    final ClassRoomProducer classRoomProducer;

    public ClassRoomService(ClassRoomRepository classRepository,
                            EnrolledStudentRepository enrolledStudentRepository,
                            MessageController messageController,
                            ClassRoomOptionRepository classRoomOptionRepository

    ) {
        this.classRepository = classRepository;
        this.enrolledStudentRepository = enrolledStudentRepository;
//        this.classRoomProducer = classRoomProducer;
        this.messageController = messageController;
        this.classRoomOptionRepository = classRoomOptionRepository;

    }



    public ClassRoom registerClassRoom(ClassRegistrationRequest Request) {
        //Getting ClassRoomOption
        ClassRoomOption classRoomOption = classRoomOptionRepository.findById((int) Request.ClassRoomOptionID())
                .orElseThrow(() -> new ResourceNotFoundException("ClassRoom Option Not found"));
        //Register a single class
        ClassRoom classRoom = ClassRoom.builder()
                .name(Request.letter())
                .schoolID(Request.schoolID())
                .level((long) Request.level())
                .classRoomOption(classRoomOption)
                .build();
        ClassRoom savedClassRoom =classRepository.save(classRoom) ;
        ClassRoomEvent classRoomEvent = getClassRoomEvent(classRoom);
//        classRoomProducer.sendMessage(classRoomEvent);
        return savedClassRoom;
    }


    //Register a list of classRoom

    public ResponseEntity<List<ClassInfoResponse>> registerClassRoomList (int schoolID,List<ClassRegistrationRequest> request) {
//        List<ClassRoom> classRoomList = new ArrayList<>();
        List<ClassInfoResponse> classInfoResponses = new ArrayList<>();
        for (ClassRegistrationRequest registrationRequest : request) {
            ClassRoomOption classRoomOption = classRoomOptionRepository.findById((int) registrationRequest.ClassRoomOptionID())
                    .orElseThrow(()-> new ResourceNotFoundException("ClassRoom Option not found"));
            ClassRoom classRoom = ClassRoom.builder()
                    .name(registrationRequest.letter())
                    .level((long) registrationRequest.level())
                    .schoolID(schoolID)
                    .classRoomOption(classRoomOption)
                    .build();
           ClassRoom savedClassRoom =  classRepository.save(classRoom);
           classInfoResponses.add(classInfoResponse(savedClassRoom));

            messageController.publishStudent(savedClassRoom);


        }

        return ResponseEntity.ok(classInfoResponses);

    }


    private ClassRoomEvent getClassRoomEvent(ClassRoom classRoom) {
        ClassRoomEvent classRoomEvent = new ClassRoomEvent();
        classRoomEvent.setClassesID(classRoom.getClassesID()) ;
        classRoomEvent.setLevel(classRoomEvent.getLevel());
        classRoomEvent.setSchoolID(classRoomEvent.getSchoolID());
        classRoomEvent.setName(classRoomEvent.getName());
//        classRoomEvent.setTeacherName(classRoom.getTeacher().getName());
        return  classRoomEvent;
    }

    public List<ClassRoom> getAllClasses() {
        return  classRepository.findAll();
    }

    public ClassRoom getClassById(Long classID ) {
        //Get the class by its id
        return classRepository.findById((long) Math.toIntExact(classID)).orElseThrow(() -> new ResourceNotFoundException("Np classroom found"));
    }


    //Add classes to a school
    //Assign the teacher to the class
//    public void assignTeacherToClass(Teacher teacher) {
//        Optional<ClassRoom> classes = getClassById(teacher.getClassID());
//        classes.ifPresent(
//                c -> {
//                    c.assignTeacher(teacher);
//                    classRepository.save(c);
//                    //todo Send teacher update
//                }
//        );
//
//    }

    //Adding Student to a class
    public void createStudent(StudentEvent studentEvent) {
        //find first the existing class

        Student enrolledStudent = new Student();
        enrolledStudent.setName(studentEvent.getName());
        enrolledStudent.setGender(studentEvent.getGender());
//        enrolledStudent.setClassID(studentEvent.getClassID());
        enrolledStudent.setDateEnrolled(new Date());
        enrolledStudent.setParentEmail(studentEvent.getParentEmail());
        enrolledStudent.setStudentID(studentEvent.getStudentID());
        enrolledStudent.setStudentClass(getClassIfExists(studentEvent.getClassID()));
        enrolledStudentRepository.save(enrolledStudent);

        //todo Publish student with class
        ResponseEntity.ok(enrolledStudent);
    }

    public ClassRoom getClassIfExists(Integer classId) {
        Optional<ClassRoom> existingClass = classRepository.findById(Long.valueOf(classId));
        if (existingClass.isEmpty()) {
            log.info("Class not found");
            return null; // Or throw an exception if desired
        }
        return existingClass.get();
    }

    // retrieve student in  a classroom
    public ResponseEntity<List<ClassStudentsResponse>> getStudentInClass(Long classId) {
        ClassRoom studentClass = classRepository.findById((long) Math.toIntExact(classId))
                .orElseThrow(() -> new RuntimeException("Class not found"));

        List<Student> students = studentClass.getStudents();
        List<ClassStudentsResponse> studentResponses = students.stream()
                .map(this:: createSimpleClassList)
                .toList();
        return ResponseEntity.ok(studentResponses);
    }

    public ClassStudentsResponse createSimpleClassList(Student student) {
        return new  ClassStudentsResponse(
//                student.getStudentClass().getTeacher(),
                student.getStudentClass().getName() ,
                Math.toIntExact(student.getStudentClass().getLevel()),
                student.getStudentClass().getClassRoomOption().getOptionName(),
                student.getStudentClass().getStudents().stream()
                        .map(this::createSimpleStudent)
                        .toList()
        );
    }
    public EnrolledStudentResponse createSimpleStudent(Student student){
        return  new EnrolledStudentResponse(
                (int) student.getStudentID(),
                student.getName()
        );
    }

    // Get Class by student ID
    public ClassInfoResponse getClassInfoByStudentID (Integer StudentID) {
        Student student = enrolledStudentRepository.findAllByStudentID(StudentID);
        ClassRoom classRoom = student.getStudentClass();

        return  classInfoResponse(classRoom);

    }
    //Create classRoom  simple request
    public ClassInfoResponse classInfoResponse(ClassRoom classRoom) {
        return new ClassInfoResponse(
                classRoom.getName(),
                Math.toIntExact(classRoom.getLevel()),
                classRoom.getName(),//make a call to school microservice to get School name.
                (int) classRoom.getSchoolID(),
                classRoom.getClassRoomOption().getOptionName()

        );

    }

    public List<ClassInfoResponse> classInfoResponseList(List<ClassRoom> classRooms){
        List<ClassInfoResponse> classInfoResponses = new ArrayList<>();
        for (ClassRoom classRoom : classRooms) {
            ClassInfoResponse  classInfoResponse = classInfoResponse(classRoom);
            classInfoResponses.add(classInfoResponse);
        }

        return  classInfoResponses ;
    }


    public int getSchoolIDbyClassID(int classRoomID) {
        ClassRoom classRoom = classRepository.findById((long) classRoomID)
                .orElseThrow(() -> new RuntimeException("No class Found"));
        return (int) classRoom.getSchoolID();
    }

    public List<ClassGeneralInformation> getClassRooms(int schoolID) {
        List<ClassRoom> classRooms = classRepository.getClassRoomBySchoolID(schoolID);
        List<ClassGeneralInformation> classGeneralInformations = new ArrayList<>();
        for (ClassRoom classRoom  : classRooms) {

            ClassGeneralInformation classGeneralInformation = new ClassGeneralInformation(
                    Math.toIntExact(classRoom.getClassesID()),
                    Math.toIntExact(classRoom.getLevel()),
                    classRoom.getName(),
                    classRoom.getStudents().size(),
                    classRoom.getCourses().size(),
                    classRoom.getClassRoomOption().getOptionName()
//                    classRoom.getTeacher() == null ? " " : classRoom.getTeacher().getName()
            );

            classGeneralInformations.add(classGeneralInformation);
        }

        return classGeneralInformations;

    }

    public List<Student> getRecentStudents(int schoolID) {
        return enrolledStudentRepository.findTopByStudentClassSchoolIDOrderByDateEnrolledDesc(schoolID,PageRequest.of(0,5));
    }

    public List<String> getParentEmailByStudentLevel(List<Long> levels) {
        /*
        *  Getting all parentEmail of student in a given classRoom
        **/
        List<String> parentEmailList = new ArrayList<>() ;
        for (Long level : levels) {
            List<ClassRoom> classRooms = classRepository.getClassRoomByLevel(level);
            for (ClassRoom classRoom : classRooms) {
                for (Student student : classRoom.getStudents()) {
                    parentEmailList.add(student.getParentEmail());
                }
            }
        }
        log.info("Emails found : " + parentEmailList);
        return parentEmailList;
    }

    public List<String> getParentEmailBySection(List<Long> sectionIDs) {
        List<String> parentEmailList = new ArrayList<>() ;
        for (Long sectionID : sectionIDs) {
            ClassRoomOption classRoomOption = classRoomOptionRepository.findClassRoomOptionByClassOptionID(sectionID);
            List<ClassRoom> classRooms = classRepository.getClassRoomByClassRoomOption(classRoomOption);
            for (ClassRoom classRoom : classRooms) {
                for (Student student : classRoom.getStudents()) {
                    parentEmailList.add(student.getParentEmail());

                }
            }
        }
        return  parentEmailList;
    }

    public Set<Long> getAllSchoolLevels  (long schoolID) {
        Set<ClassRoom> classRooms = classRepository.getAllBySchoolID(schoolID);
        Set<Long> levels = new HashSet<>();
        for(ClassRoom classRoom : classRooms) {
            levels.add(classRoom.getLevel());
        }
        return  levels ;
    }


    public List<Student> getAllSchoolStudent(long schoolID) {
        List<ClassRoom> classRooms = classRepository.getClassRoomsBySchoolID(schoolID);
        List<Student> allStudents = new ArrayList<>();

        for (ClassRoom classRoom : classRooms) {
            allStudents.addAll(classRoom.getStudents());
        }
        return allStudents;
    }

    public List<Student> getStudentEnrolledClassroom(long classRoomID) {
        ClassRoom classRoom = classRepository.findById( classRoomID)
                .orElseThrow(() -> new ResourceNotFoundException("ClassRoom not found") );
        return  classRoom.getStudents();
    }

    public List<CoursesResponse> getAllCourses(long schoolID) {
        //getting all classrooms
        List<CoursesResponse> courseList = new ArrayList<>();

        List<ClassRoom> classRoomList = classRepository.getClassRoomBySchoolID(schoolID);
        for (ClassRoom classRoom : classRoomList) {
            CoursesResponse coursesResponse = new CoursesResponse(
                    classRoom.getClassesID() ,
                    classRoom.getLevel() + classRoom.getName() ,
                    simpleCourseFormList(classRoom.getCourses())

            );
            courseList.add(coursesResponse);

        }

        return  courseList ;
    }

    public  List<SimpleCourseForm> simpleCourseFormList (List<Course> courses) {
        List<SimpleCourseForm> simpleCourseFormList = new ArrayList<>();
        for (Course course : courses) {
            SimpleCourseForm simpleCourseForm = new SimpleCourseForm(
                    course.getName() ,
                    course.getCategory(),
                    course.getTeacher() == null  ? "" : course.getTeacher().getName() ,
                    course.getClassRoom().getLevel()+ course.getClassRoom().getName(),
                    course.getCourseID()

            );
            simpleCourseFormList.add(simpleCourseForm);
        }

        return  simpleCourseFormList;
    }

//    public List<CoursesAssigned> getCoursesAssignedByTeacherID(long teacherID) {
//        Teacher teacher =
//    }


//    public ResponseEntity<String> updateClassRoomOption(long schoolID, long classRoomOptionID) {
//        ClassRoomOption
//    }
}
