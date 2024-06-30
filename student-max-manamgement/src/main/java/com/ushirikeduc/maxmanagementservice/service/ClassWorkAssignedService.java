package com.ushirikeduc.maxmanagementservice.service;

import Dto.ClassWorkEvent;
import com.ushirikeduc.maxmanagementservice.Dto.ClassWorksList;
import com.ushirikeduc.maxmanagementservice.Dto.GradedClassWorkByStudent;
import com.ushirikeduc.maxmanagementservice.model.ClassWorksAssigned;
import com.ushirikeduc.maxmanagementservice.model.MaxOwner;
import com.ushirikeduc.maxmanagementservice.model.Score;
import com.ushirikeduc.maxmanagementservice.repository.ClassworkRepository;
import com.ushirikeduc.maxmanagementservice.repository.MaxOwnerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public record ClassWorkAssignedService(
        ClassworkRepository classworkRepository ,
        MaxOwnerRepository maxOwnerRepository

) {

    public void registerAssignedClassWork(ClassWorkEvent classWorkEvent) {

        ClassWorksAssigned classwork = ClassWorksAssigned.builder()

                .courseName(classWorkEvent.getCourseName())
                .classWorkType(classWorkEvent.getClassWorkType())
                .classRoomID(classWorkEvent.getClassID())
                .courseID(classWorkEvent.getCourseID())
                .maxScore(classWorkEvent.getMaxScore())
                .isGraded(false)
                .classWorkID(classWorkEvent.getClassWorkID())
                .build();

        classworkRepository.save(classwork);
    }

    public List<GradedClassWorkByStudent> getRecentGradedClasswork(long studentID) {
        MaxOwner maxOwner = maxOwnerRepository.findMaxOwnerByStudentID(studentID);
        List<ClassWorksAssigned> classWorksAssigned = classworkRepository.findClassWorksAssignedByClassRoomID(Math.toIntExact(maxOwner.getClassID()));
//        List<ClassWorEksAssigned> gradedClassWorks = new ArrayList<>();
        List<GradedClassWorkByStudent> gradedClassWorkByStudents  = new ArrayList<>();

        for (ClassWorksAssigned classWork : classWorksAssigned) {
            if (classWork.isGraded()) {
                for (Score score : classWork.getScores()) {
                    if (score.getStudent().getStudentID().equals(studentID)) {
//                        gradedClassWorks.add(classWork);
                        GradedClassWorkByStudent gradedClassWorkByStudent = new GradedClassWorkByStudent(
                                classWork.getCourseName(),
                                classWork.getClassWorkID(),
                                score.getScore() ,
                                classWork.getMaxScore(),
                                classWork.getGradedDate() ,
                                true,
                                score.getTeacherComment()
                        );
                        gradedClassWorkByStudents.add(gradedClassWorkByStudent);

                        break; // No need to check other scores for this class work
                    }
                }
            } else  {

                        GradedClassWorkByStudent gradedClassWorkByStudent = new GradedClassWorkByStudent(
                                classWork.getCourseName(),
                                classWork.getClassWorkID(),
                              0 ,
                                classWork.getMaxScore(),
                                classWork.getGradedDate(),
                                false,
                                ""

                        );
                        gradedClassWorkByStudents.add(gradedClassWorkByStudent);

            }
        }

        return  gradedClassWorkByStudents;
    }


}
