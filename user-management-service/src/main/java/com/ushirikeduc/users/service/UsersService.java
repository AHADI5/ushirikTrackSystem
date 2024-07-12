package com.ushirikeduc.users.service;

import com.ushirikeduc.users.dtoRequests.UserStat;
import com.ushirikeduc.users.model.Role;
import com.ushirikeduc.users.model.Users;
import com.ushirikeduc.users.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public record UsersService(
        UserRepository userRepository
) {

    public UserStat getUsersStatBySchoolID(int schoolID) {
        List<Users> parents  = userRepository.findUsersBySchoolIDAndRole(schoolID, Role.PARENT);
        List<Users> teachers =  userRepository.findUsersBySchoolIDAndRole(schoolID, Role.TEACHER);
        List<Users> directors  = userRepository.findUsersBySchoolIDAndRole(schoolID, Role.DIRECTOR);
         return  new UserStat(
                teachers.size(),
                parents.size(),
                directors.size()
        );


    }
}
