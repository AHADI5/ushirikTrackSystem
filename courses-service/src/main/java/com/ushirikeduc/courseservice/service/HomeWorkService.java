package com.ushirikeduc.courseservice.service;


import com.ushirikeduc.courseservice.dto.HomeworkRegistrationRequest;
import com.ushirikeduc.courseservice.dto.QuestionRegistrationRequest;
import com.ushirikeduc.courseservice.dto.QuestionResponse;
import com.ushirikeduc.courseservice.dto.homeWorkResponse;
import com.ushirikeduc.courseservice.model.Course;
import com.ushirikeduc.courseservice.model.HomeWorkQuestion;
import com.ushirikeduc.courseservice.model.Homework;
import com.ushirikeduc.courseservice.repository.CourseRepository;
import com.ushirikeduc.courseservice.repository.HomeWorkRepository;
import com.ushirikeduc.courseservice.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
@Slf4j
public record HomeWorkService (
        HomeWorkRepository homeWorkRepository ,
        QuestionRepository questionRepository ,
        CourseRepository courseRepository
) {
    public homeWorkResponse registerHomeWork(int classID, HomeworkRegistrationRequest request) {
        // Build the Homework object first
        Homework homework = Homework.builder()
                .creationDate(new Date())
                .title(request.title())
                .classRoomID(classID)
                .description(request.Description())
                .build();

        // Save the Homework entity
        Homework savedHomework = homeWorkRepository.save(homework);
        log.info("Saved homework " + savedHomework);

        // List to hold the saved questions
        List<HomeWorkQuestion> savedQuestions = new ArrayList<>();

        // Get Questions from the request
        for (QuestionRegistrationRequest question : request.questions()) {
            HomeWorkQuestion homeWorkQuestion = new HomeWorkQuestion();
            homeWorkQuestion.setQuestion(question.question());
            homeWorkQuestion.setDescription(question.description());
            homeWorkQuestion.setInstruction(question.instruction());

            // Getting the question course concerned
            Course course = courseRepository.findById(question.courseID())
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

            // Set the question's course
            homeWorkQuestion.setCourse(course);

            // Set the question's homework
            homeWorkQuestion.setHomework(savedHomework);

            // Save the HomeWorkQuestion entity
            HomeWorkQuestion savedQuestion = questionRepository.save(homeWorkQuestion);

            // Add the saved question to the list
            savedQuestions.add(savedQuestion);
        }

        // Set the list of questions to the Homework entity
        savedHomework.setQuestions(savedQuestions);

        return simpleHomework(savedHomework);
    }



    public homeWorkResponse simpleHomework (Homework homework) {

        /**
         *
         * @HomeWork
         * This function returns a simplified form of a question
         *
         * */

        List<String> coursesInvolved = new ArrayList<>();
        List<QuestionResponse> simpleQuestions = new ArrayList<>();

        //Getting the list of courses involved
        for(HomeWorkQuestion question : homework.getQuestions()) {
            String courseName = question.getCourse().getName();
            coursesInvolved.add(courseName);
        }

        for(HomeWorkQuestion question : homework.getQuestions()) {
            QuestionResponse simpleQuestion = simmpleQuestionResponse(question);
            simpleQuestions.add(simpleQuestion);

        }

        //Getting simplified question object
        log.info("the date saved is  " + homework.getCreationDate() );

        return new homeWorkResponse(
                coursesInvolved ,
                homework.getTitle(),
                homework.getDescription(),
                homework.getCreationDate(),
                simpleQuestions
        );
    }

    public QuestionResponse simmpleQuestionResponse(HomeWorkQuestion question) {
       /**
        * @HomeWorkQuestion
        * This function returns a simplified form of a question
        *
        * */

        return  new QuestionResponse(
                question.getQuestion(),
                question.getDescription(),
                question.getInstruction(),
                question.getCourse().getName()
        );

    }


    public List<homeWorkResponse> getHomeWorksByClassID(int classID) {
        /**
         *
         * @classID
         * This function returns a list of homeworks by classID
         */
        List<homeWorkResponse>  homeWorkResponses = new ArrayList<>();
        List<Homework> homeworks =  homeWorkRepository.findAllByClassRoomID(classID);
        for(Homework homework : homeworks) {
            homeWorkResponse homeWorkResponse = simpleHomework(homework);
            homeWorkResponses.add(homeWorkResponse);
        }
        return homeWorkResponses ;
    }


}
