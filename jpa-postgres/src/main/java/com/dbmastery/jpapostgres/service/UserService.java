package com.dbmastery.jpapostgres.service;

import com.dbmastery.jpapostgres.entity.UserEntity;
import com.dbmastery.jpapostgres.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
    }

    @Transactional
    public UserEntity create(UserEntity user) {
        log.info("Creating user: {}", user.getEmail());
        return userRepository.save(user);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
