package com.ushirikeduc.schools.repository;

import com.ushirikeduc.schools.model.Communique;
import com.ushirikeduc.schools.model.School;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommuniqueRepository extends JpaRepository<Communique , Integer> {
 public List<Communique> findTop5BySchoolOrderByDateCreatedDesc(School school, PageRequest pageRequest);
}
