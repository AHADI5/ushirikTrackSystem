package com.ushirikeduc.maxmanagementservice.repository;

import com.ushirikeduc.maxmanagementservice.model.MaxOwner;
import com.ushirikeduc.maxmanagementservice.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaxOwnerRepository extends JpaRepository<MaxOwner ,Integer> {
    public MaxOwner findMaxOwnerByStudentID(Long studentID);
    public List<MaxOwner> findMaxOwnersByClassID(Long classID);
}
