package com.ushirikeduc.users.repository;


import com.ushirikeduc.users.model.Role;
import com.ushirikeduc.users.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByEmail(String email);
    List<Users> findUsersBySchoolIDAndRole(int schoolID, Role role) ;
    List<Users> findUsersBySchoolID(int schoolID) ;
}
