package com.ushirikeduc.student.services;

import com.ushirikeduc.student.model.ClassWorksAssigned;
import com.ushirikeduc.student.model.Score;
import com.ushirikeduc.student.model.Student;
import com.ushirikeduc.student.repository.ClassWorkAssignedRepository;
import com.ushirikeduc.student.repository.StudentRepository;
import com.ushirikeduc.student.repository.StudentScoreRepository;
import com.ushirikeduc.student.request.ScoreRequest;
import com.ushirikeduc.student.request.ScoreResponse;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public record ScoreService(
        StudentRepository studentRepository ,
        StudentService studentService ,
        StudentScoreRepository studentScoreRepository,
        ClassWorkAssignedRepository classWorkAssignedRepository

) {
    public List<Score> recordScore(int classWorkID , List<ScoreRequest> request) {
        //getting the classWork if exists
        ClassWorksAssigned classWork = classWorkAssignedRepository.findClassWorksAssignedByClassWorkID(classWorkID);
        //Getting the student
        List<Score> savedScoreRequests = new ArrayList<>();


        for (ScoreRequest score : request){
            Student student =  studentRepository.findById(score.studentID())
                    .orElseThrow(() -> new ResourceNotFoundException("Student's Id incorrect"));
            Score studentScore = Score.builder()
                    .score(score.score())
                    .student(student)
                    .classwork(classWork)
                    .build();
           savedScoreRequests.add(studentScoreRepository.save(studentScore));
        }
        return savedScoreRequests;
    }

    public List<ScoreResponse> getScoreByStudentID(int studentID){
        Student student = studentRepository.findById(studentID)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
        List<Score> studentScore = studentScoreRepository.getScoreByStudent(student);
        List<ScoreResponse> studentScoreList =new ArrayList<>() ;

        for (Score score : studentScore) {
            int ScoreID = Math.toIntExact(score.getId());
            //find Real Score
            Score realScore = studentScoreRepository.findById(ScoreID)
                    .orElseThrow(() -> new ResourceNotFoundException("Score Not found"));

            ScoreResponse scoreResponse = new ScoreResponse(
                    realScore.getClasswork().getCourseName(),realScore.getClasswork().getTitle(), realScore.getScore()
            );
            studentScoreList.add(scoreResponse);

        }
        return  studentScoreList;

    }

//    public Course getcourseByIdInClassRoom(Long classRoomId, int courseId) {
//        ClassRoom classRoom = classRoomRepository.findById(classRoomId)
//                .orElseThrow(() -> new ResourceNotFoundException("Class Not found"));
//        return  classRoom.getCourses().stream()
//                .filter(course -> course.getCourseID()==(courseId))
//                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Course Not found"));
//
//    }


}
