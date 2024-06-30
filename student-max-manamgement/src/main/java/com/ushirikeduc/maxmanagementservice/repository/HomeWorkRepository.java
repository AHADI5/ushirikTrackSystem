package com.ushirikeduc.maxmanagementservice.repository;

import com.ushirikeduc.maxmanagementservice.model.HomeWorkAssigned;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeWorkRepository extends JpaRepository<HomeWorkAssigned , Integer> {
}
