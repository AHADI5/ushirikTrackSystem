package com.ushirikeduc.schools.repository;

import com.ushirikeduc.schools.model.SchoolAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<SchoolAdmin , Integer> {
    public SchoolAdmin findSchoolAdminByEmail(String adminEmail);
}
