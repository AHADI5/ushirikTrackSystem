package com.ushirikeduc.schools.repository;

import com.ushirikeduc.schools.model.SchoolEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<SchoolEvent,Integer > {
}
