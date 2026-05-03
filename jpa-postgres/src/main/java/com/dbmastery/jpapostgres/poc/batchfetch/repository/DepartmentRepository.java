package com.dbmastery.jpapostgres.poc.batchfetch.repository;

import com.dbmastery.jpapostgres.poc.batchfetch.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
