package com.dbmastery.jpapostgres.poc.projections.repository;

import com.dbmastery.jpapostgres.poc.projections.dto.UserAdminView;
import com.dbmastery.jpapostgres.poc.projections.dto.UserDto;
import com.dbmastery.jpapostgres.poc.projections.dto.UserSummary;
import com.dbmastery.jpapostgres.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectionUserRepository extends JpaRepository<UserEntity, Long> {

    // ── 1. Interface projection ──────────────────────────────────────────────
    // Spring Data inspects UserSummary getters → emits SELECT name, email
    List<UserSummary> findAllProjectedBy();

    // ── 2. DTO (record) projection via JPQL constructor ──────────────────────
    // Explicit: tells Hibernate exactly what to SELECT
    @Query("SELECT new com.dbmastery.jpapostgres.poc.projections.dto.UserDto(u.name, u.email, CAST(u.role AS string)) FROM UserEntity u")
    List<UserDto> findAllAsDto();

    // ── 3. Dynamic projection — caller decides the return type ───────────────
    // Same query, different shape depending on <T>
    // Usage: repo.findByRole("ADMIN", UserSummary.class)   → lightweight
    //        repo.findByRole("ADMIN", UserAdminView.class) → full admin view
    <T> List<T> findByRole(UserEntity.UserRole role, Class<T> type);
}
