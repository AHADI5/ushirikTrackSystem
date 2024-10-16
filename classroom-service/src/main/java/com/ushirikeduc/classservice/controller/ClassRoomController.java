package com.ushirikeduc.classservice.controller;

import com.ushirikeduc.classservice.dto.*;
import com.ushirikeduc.classservice.dto.Communication.CommunicationCorrespondents;
import com.ushirikeduc.classservice.dto.TimeTable.TimeTableRequest;
import com.ushirikeduc.classservice.model.*;
import com.ushirikeduc.classservice.repository.ClassRoomEventRepository;
import com.ushirikeduc.classservice.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@Slf4j
@RequestMapping("api/v1/classroom")
public class ClassRoomController {
    private final ClassRoomService classRoomService;
    private final CoursesService coursesService;
    private  final ClassRoomOptionService classRoomOptionService;
    private  final ClassRoomEventService classRoomEventService;
    private  final HomeWorkServices homeWorkServices ;
    private final TimTableService timTableService;

    public ClassRoomController(ClassRoomService classRoomService, CoursesService coursesService, ClassRoomService classRoomOptionService, ClassRoomOptionService classRoomOptionService1, ClassRoomEventRepository classRoomEventRepository, ClassRoomEventService classRoomEventService, HomeWorkServices homeWorkServices, TimTableService timTableService) {
        this.classRoomService = classRoomService;
        this.coursesService = coursesService;
        this.classRoomOptionService = classRoomOptionService1;
        this.classRoomEventService = classRoomEventService;
        this.homeWorkServices = homeWorkServices;
        this.timTableService = timTableService;
    }

    @PostMapping("/newClassRoom")
    public com.ushirikeduc.classservice.model.ClassRoom registerClassRoom(@RequestBody ClassRegistrationRequest classRoom) {
        return classRoomService.registerClassRoom(classRoom);

    }

    @GetMapping("/{classRoomID}")
    public ClassRoom getClassRoomById(@PathVariable Integer classRoomID) {
        return  classRoomService.getClassById(Long.valueOf(classRoomID));
    }

    @GetMapping("/course/{courseID}")
    public Course getCourseByID(@PathVariable int courseID) {
        return coursesService.getCourseByID(courseID);
    }
    @GetMapping("/{classRoomId}/get-courses")
    public List<Course> getCoursesByClassRoom(@PathVariable  int classRoomId) {
        return  coursesService.courseByClassID(classRoomId);
    }
    @GetMapping("{schoolID}/get-students-stats")
    public Set<ClassRoomStat> getStudentStatsByLevel (@PathVariable int schoolID) {
      return  classRoomService.getStudentNumberPerLevel(schoolID);
  }


    @GetMapping("/{classRoomId}/courses/{courseId}")
    public Course getSpecieCourseInClassRoom(
            @PathVariable int classRoomId, @PathVariable int courseId
    ) {
        return coursesService.getcourseByIdInClassRoom((long) classRoomId, courseId);

    }
    @GetMapping("/{studentID}/class")
    public ClassInfoResponse  getClassInfosByStudentID (@PathVariable Integer studentID) {

        return classRoomService.getClassInfoByStudentID(studentID);
    }

    @GetMapping("{classID}/schoolID")
    public int getSchoolIDByClassID(@PathVariable int classID) {
        return  classRoomService.getSchoolIDbyClassID(classID) ;
    }

    @GetMapping("{schoolID}/classrooms")
    public List<ClassGeneralInformation> getClassRoomsBySchoolID(@PathVariable int schoolID){
        return classRoomService.getClassRooms(schoolID) ;
    }

    //Register a list of classRoom
    @PostMapping("/{schoolID}/registerClassRoom")
    public ResponseEntity<List<ClassInfoResponse>> registerClassRoomList(@PathVariable int schoolID, @RequestBody List<ClassRegistrationRequest> requests) {
        return classRoomService.registerClassRoomList(schoolID,requests);
    }

    @GetMapping("/recentStudents")
    public List<Student> getRecentStudents(int schoolID) {
        return classRoomService.getRecentStudents(schoolID) ;
    }

    @GetMapping("/{classRoomID}/getStudentWithinClassRoom")
    public List<Student> getStudentWithinClassRoom(@PathVariable long classRoomID ) {
        return classRoomService.getStudentEnrolledClassroom(classRoomID);

    }

    @PostMapping("/courses/assign-course")
    public CoursesAssigned assignCourseToTeachers (@RequestBody AssignCoursesRequest request){
        return  coursesService.assignCourseToTeacher( request);
    }

    @PostMapping("/assignTitular")
    public ResponseEntity<String> assignTitular (@RequestBody AssignPrincipalTeacherRequest request) {
        return  classRoomService.assignTitularTeacher(request);

    }

    @GetMapping("{classID}/getTitular")
    public TeacherResponse getClassRoomTitular(@PathVariable int classID) {
        return classRoomService.getClassRoomTitular(classID);

    }

    @PostMapping("/{schoolID}/new-classRoomOption")
    public  ResponseEntity<ClassRoomOption> registerNewClassRoomOption (@PathVariable int schoolID,@RequestBody ClassRoomOptionRequest request) {

        return classRoomOptionService.createClassRoomOption(schoolID,request);

    }
    @PostMapping("{classID}/newEvent")
    public ClassRoomEventResponse registerEvent (@PathVariable int classID,
                                        @RequestBody EventRegisterRequest request) {
        return  classRoomEventService.registerNewEvent(classID , request);

    }

    @GetMapping("{classID}/getEventsByClassID")
    public  List<ClassRoomEventResponse> getEventsByClassID (@PathVariable int classID) {
        return  classRoomEventService.getClassRoomEventysByClassRoomID(classID);
    }

//    @PutMapping("/{schoolID}/{classRoomOptionID}/modify-classroomOption")
//    public ResponseEntity<String> updateClassRoomOptionInformations(
//            @PathVariable long schoolID ,
//            @PathVariable long classRoomOptionID
//    ) {
//
//        return  classRoomService.updateClassRoomOption (schoolID , classRoomOptionID) ;
//
//    }

    @GetMapping("/{schoolID}/get-section")
    public ResponseEntity<List<ClassRoomOptionResponse>> getAllClassOptionsByClassRoom(@PathVariable int schoolID ){
        return  classRoomOptionService.getAllClassSection(schoolID);
    }
    @GetMapping("{teacherEmail}/getTeacherByEmail")
    public SimpleTeacherForm getTeacherByEmail(@PathVariable String teacherEmail){
        return classRoomService.loadTeacherByEmail(teacherEmail);
    }





    @PostMapping("/studentLevel/parentEmail")
    public List<String> getParentEmailByStudentLevel(@RequestBody List<Integer> levels) {
        return classRoomService.getParentEmailByStudentLevel(levels) ;
    }

    @PostMapping("/studentSection/parentEmail")
    public List<String> getParentEmailByStudentSection(@RequestBody List<Integer> sectionID) {
        return classRoomService.getParentEmailBySection(sectionID) ;
    }

    @GetMapping("{schoolID}/classroomLevels")
    public List<Long> getAllSchoolLevel(@PathVariable Integer schoolID) {
        return classRoomService.getAllSchoolLevels(schoolID) ;

    }

    @GetMapping("{schoolID}/getAllStudents")
    public List<Student> getAllStudent (@PathVariable long schoolID){
        return classRoomService.getAllSchoolStudent(schoolID);
    }

    @GetMapping("{schoolID}/getAllCourses")
    public List<CoursesResponse> getAllClassRooms (@PathVariable long schoolID) {
        return  classRoomService.getAllCourses(schoolID);
    }



    @GetMapping("{teacherID}/getAllCoursesAssigned")
    public List<SimpleCourseForm> getClassesAssigned (@PathVariable long teacherID) {
        return coursesService.getCoursesAssignedByTeacherID(teacherID) ;

    }

    //Communique Recipient
    @GetMapping("/{schoolID}/communicationCorespondent")
    public List<CommunicationCorrespondents> getCommunicationRespondents(@PathVariable long schoolID) {
        return classRoomService.getCommunicationCorrespondent((int) schoolID) ;
    }



    //HomeWorks end Point

    @PostMapping("/homeWork/reportHomeWork/")
    public void  reportHomeWork(@RequestBody ReportHomeWorkRequest request) {
        homeWorkServices.reportHomeWork(request);
    }


   @PostMapping("{classRoomID}/registerTimeTable")
   public TimeTable registerTimeTable(
           @PathVariable long classRoomID ,
           @RequestBody TimeTableRequest request) {
       return timTableService.registerTimeTable(classRoomID, request);

   }
}
