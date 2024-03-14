package com.ushirikeduc.student.repository;

import com.ushirikeduc.student.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
