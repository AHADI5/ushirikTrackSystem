package com.ushirikeduc.schools.interfaces;

import com.ushirikeduc.schools.model.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolInterface extends JpaRepository<School, Integer> {
}
