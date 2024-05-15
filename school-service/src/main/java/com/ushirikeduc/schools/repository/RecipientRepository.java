package com.ushirikeduc.schools.repository;

import com.ushirikeduc.schools.model.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipientRepository extends JpaRepository<Recipient, Integer> {
}
