package com.ushirikeduc.classservice.kafka;

import Dto.CourseEvent;
import Dto.StudentEvent;
import com.ushirikeduc.classservice.model.Course;
import com.ushirikeduc.classservice.service.CoursesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public record CourseConsumer(
        CoursesService coursesService
) {
    @KafkaListener(
            topics = "create-course",
            groupId = "course"
    )
    public void consume(CourseEvent courseEvent) {
        log.info(String.format("Student Event received ClassRoom  => %s ", courseEvent.toString()));
        //todo find the selected class
        //todo create student and associate him with the existing class
        coursesService.registerCourse(courseEvent);

    }
}
