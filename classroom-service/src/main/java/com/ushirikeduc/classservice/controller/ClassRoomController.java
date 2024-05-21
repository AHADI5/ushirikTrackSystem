package com.ushirikeduc.classservice.controller;

import com.ushirikeduc.classservice.dto.*;
import com.ushirikeduc.classservice.model.ClassRoom;
import com.ushirikeduc.classservice.model.ClassRoomOption;
import com.ushirikeduc.classservice.model.Course;
import com.ushirikeduc.classservice.model.Student;
import com.ushirikeduc.classservice.service.ClassRoomOptionService;
import com.ushirikeduc.classservice.service.ClassRoomService;
import com.ushirikeduc.classservice.service.CoursesService;
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

    public ClassRoomController(ClassRoomService classRoomService, CoursesService coursesService, ClassRoomService classRoomOptionService, ClassRoomOptionService classRoomOptionService1) {
        this.classRoomService = classRoomService;
        this.coursesService = coursesService;

        this.classRoomOptionService = classRoomOptionService1;
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

    @PostMapping("/{schoolID}/new-classRoomOption")
    public  ResponseEntity<ClassRoomOption> registerNewClassRoomOption (@PathVariable int schoolID,@RequestBody ClassRoomOptionRequest request) {

        return classRoomOptionService.createClassRoomOption(schoolID,request);

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

    @PostMapping("/studentLevel/parentEmail")
    public List<String> getParentEmailByStudentLevel(@RequestBody List<Long> levels) {
        return classRoomService.getParentEmailByStudentLevel(levels) ;
    }

    @PostMapping("/studentSection/parentEmail")
    public List<String> getParentEmailByStudentSection(@RequestBody List<Long> sectionID) {
        return classRoomService.getParentEmailBySection(sectionID) ;
    }

    @GetMapping("{schoolID}/classroomLevels")
    public Set<Long> getAllSchoolLevel(@PathVariable long schoolID) {
        return classRoomService.getAllSchoolLevels(schoolID) ;

    }

    @GetMapping("{schoolID}/getAllStudents")
    public List<Student> getAllStudent (@PathVariable long schoolID){
        return classRoomService.getAllSchoolStudent(schoolID);
    }



}
