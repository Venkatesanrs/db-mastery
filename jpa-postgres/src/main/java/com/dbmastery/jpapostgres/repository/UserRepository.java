package com.dbmastery.jpapostgres.repository;

import com.dbmastery.jpapostgres.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findByRole(UserEntity.UserRole role);

    // POC: JPQL named query
    @Query("SELECT u FROM UserEntity u WHERE u.name LIKE %:keyword%")
    List<UserEntity> searchByName(String keyword);

    // POC: Native query
    @Query(value = "SELECT * FROM users WHERE email ILIKE %:domain%", nativeQuery = true)
    List<UserEntity> findByEmailDomain(String domain);
}
