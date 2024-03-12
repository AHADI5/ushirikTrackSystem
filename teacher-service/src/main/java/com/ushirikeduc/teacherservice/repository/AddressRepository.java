package com.ushirikeduc.teacherservice.repository;

import com.ushirikeduc.teacherservice.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Integer> {
}
