package com.ushirikeduc.courseservice.repository;

import com.ushirikeduc.courseservice.model.CourseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseCategoryRepository  extends JpaRepository<CourseCategory , Integer> {
    public List<CourseCategory> getCourseCategoriesBySchoolID(long schoolID);

}
