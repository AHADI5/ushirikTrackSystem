package com.ushirikeduc.schools.repository;

import com.ushirikeduc.schools.model.CommunicationType;
import com.ushirikeduc.schools.model.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommuniqueTypeRepository  extends JpaRepository<CommunicationType, Long> {
    public List<CommunicationType> findCommunicationTypesBySchool (School school);


}
