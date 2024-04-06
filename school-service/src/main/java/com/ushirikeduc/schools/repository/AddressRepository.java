package com.ushirikeduc.schools.repository;

import com.ushirikeduc.schools.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Integer> {
}
