package com.ushirikeduc.schools.interfaces;

import com.ushirikeduc.schools.model.Classes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<Classes, Integer> {
}
