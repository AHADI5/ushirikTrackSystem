package com.ushirikeduc.courseservice.repository;

import com.ushirikeduc.courseservice.model.HomeWorkQuestion;
import com.ushirikeduc.courseservice.model.Homework;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeWorkRepository extends JpaRepository<Homework , Long> {
    public List<Homework> findAllByClassRoomID(Integer classRoomID);
}
