package com.dbmastery.jpapostgres.poc.projections.service;

import com.dbmastery.jpapostgres.entity.UserEntity;
import com.dbmastery.jpapostgres.poc.projections.dto.UserAdminView;
import com.dbmastery.jpapostgres.poc.projections.dto.UserDto;
import com.dbmastery.jpapostgres.poc.projections.dto.UserSummary;
import com.dbmastery.jpapostgres.poc.projections.repository.ProjectionUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * POC: Projections comparison
 *
 * Key takeaway — watch the SQL log for each call:
 *
 *   findAllEntities()      → SELECT id, name, email, role, created_at, updated_at FROM users
 *   findSummaries()        → SELECT name, email FROM users
 *   findDtos()             → SELECT name, email, role FROM users
 *   findAdminViews()       → SELECT id, name, email, role, created_at FROM users
 *
 * Projections reduce data transfer and avoid loading columns you don't need.
 * On wide tables (30+ columns) this matters significantly.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectionService {

    private final ProjectionUserRepository userRepository;

    @Transactional(readOnly = true)
    public List<UserEntity> findAllEntities() {
        log.info("=== Full entity load (all columns) ===");
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<UserSummary> findSummaries() {
        log.info("=== Interface projection (name, email only) ===");
        return userRepository.findAllProjectedBy();
    }

    @Transactional(readOnly = true)
    public List<UserDto> findDtos() {
        log.info("=== DTO record projection (name, email, role) ===");
        return userRepository.findAllAsDto();
    }

    @Transactional(readOnly = true)
    public List<UserAdminView> findAdminViews() {
        log.info("=== Dynamic projection → UserAdminView ===");
        return userRepository.findByRole(UserEntity.UserRole.ADMIN, UserAdminView.class);
    }

    @Transactional(readOnly = true)
    public List<UserSummary> findAdminSummaries() {
        log.info("=== Dynamic projection → UserSummary ===");
        return userRepository.findByRole(UserEntity.UserRole.ADMIN, UserSummary.class);
    }
}
