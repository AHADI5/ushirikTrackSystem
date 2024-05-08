package com.ushirikeduc.users.repository;

import com.ushirikeduc.users.model.UserOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOperationRepository extends JpaRepository<UserOperation, Integer> {
}
