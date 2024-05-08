package com.ushirikeduc.users.service;

import com.ushirikeduc.users.UserManagementApplication;
import com.ushirikeduc.users.model.Operation;
import com.ushirikeduc.users.model.UserOperation;
import com.ushirikeduc.users.model.Users;
import com.ushirikeduc.users.repository.UserOperationRepository;
import com.ushirikeduc.users.repository.UserRepository;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public record UserOperationService(
        UserOperationRepository userOperationRepository,
        UserRepository userRepository
) {

    public ResponseEntity<String> registerOperation(String email , Operation operation) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User Not found !!!"));

        UserOperation userOperation = UserOperation.builder()
                .date(new Date())
                .operation(operation)
                .user(user)
                .build();
        userOperationRepository.save(userOperation);

        return ResponseEntity.ok("Operation Saved");
    }
}
