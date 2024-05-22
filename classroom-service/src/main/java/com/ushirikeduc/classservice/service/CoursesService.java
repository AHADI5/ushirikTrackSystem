package com.ushirikeduc.classservice.service;

import Dto.CourseEvent;
import com.ushirikeduc.classservice.dto.AssignCoursesRequest;
import com.ushirikeduc.classservice.dto.CoursesAssigned;
import com.ushirikeduc.classservice.dto.SimpleCourseForm;
import com.ushirikeduc.classservice.model.ClassRoom;
import com.ushirikeduc.classservice.model.Course;
import com.ushirikeduc.classservice.model.Teacher;
import com.ushirikeduc.classservice.repository.ClassRoomRepository;
import com.ushirikeduc.classservice.repository.CourseRepository;
import com.ushirikeduc.classservice.repository.TeacherRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public record CoursesService (
        ClassRoomService classRoomService,
        ClassRoomRepository classRoomRepository,
        CourseRepository courseRepository  ,
        TeacherService teacherService,
        TeacherRepository teacherRepository


) {

    public void registerCourse(CourseEvent courseEvent) {
        //fetching the classroom from the database
        ClassRoom classRoom = classRoomRepository.findById((long) courseEvent.getClassId())
                .orElseThrow(() -> new ResourceNotFoundException("ClassRoom Not found"));
        Course course = Course.builder()
                .name(courseEvent.getName())
                .category(courseEvent.getCategory())
                .classRoom(classRoom)
                .courseID(courseEvent.getCourseID())
                .build();

        courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    public Course getCourseByID(int courseID) {
        return courseRepository.findById(courseID).orElseThrow(
                () -> new ResourceNotFoundException("not found")
        );
    }

    public List<Course> courseByClassID(int classRoomID) {
        ClassRoom classRoom = classRoomRepository.findById((long) classRoomID)
                .orElseThrow(() ->new ResourceNotFoundException("Course not found"));
        return  classRoom.getCourses();
    }

    public Course getcourseByIdInClassRoom(Long classRoomId, int courseId) {
        ClassRoom classRoom = classRoomRepository.findById(classRoomId)
                .orElseThrow(() -> new ResourceNotFoundException("Class Not found"));
        return  classRoom.getCourses().stream()
                .filter(course -> course.getCourseID()==(courseId))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Course Not found"));

    }
    public CoursesAssigned assignCourseToTeacher( AssignCoursesRequest request) {

//        ClassRoom classRoom = classRoomService.getClassIfExists(classID);
        Teacher teacher = teacherService.getTeacherByID(request.teacherID());
//        log.info(teacher.toString());
        List<Course> courses = new ArrayList<>();
        for (int ID : request.courseIDs()) {
            Course course = courseRepository.findCourseByCourseID(ID);
            course.setTeacher(teacher);
            courses.add(courseRepository.save(course));
        }
        teacher.setCourses(courses);
        Teacher updatedTeacher  = teacherRepository.save(teacher);
        return new CoursesAssigned(
                updatedTeacher.getName(),
                updatedTeacher.getCourses()
        );
    }

    public List<SimpleCourseForm> getCoursesAssignedByTeacherID(long teacherID) {
        Teacher teacher  = teacherRepository.getTeacherByTeacherID(teacherID);
        List<SimpleCourseForm> coursesAssigned = new ArrayList<>();

        for (Course course:teacher.getCourses()) {
            SimpleCourseForm simpleCourseForm = new SimpleCourseForm(
                    course.getName() ,
                    course.getCategory() ,
                    course.getTeacher().getName() ,
                    course.getClassRoom().getLevel() + " " + course.getClassRoom().getName() + " " +course.getClassRoom().getClassRoomOption().getOptionName(),
                    course.getCourseID()
            );

            coursesAssigned.add(simpleCourseForm);


        }

        return  coursesAssigned ;
    }
}
