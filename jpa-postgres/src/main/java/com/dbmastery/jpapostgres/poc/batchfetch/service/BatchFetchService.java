package com.dbmastery.jpapostgres.poc.batchfetch.service;

import com.dbmastery.jpapostgres.poc.batchfetch.entity.Department;
import com.dbmastery.jpapostgres.poc.batchfetch.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * POC: Batch Fetching with @BatchSize
 *
 * Key insight: @BatchSize is a middle ground between:
 *   - N+1 (one query per parent)          ← too many queries
 *   - JOIN FETCH (cartesian product risk)  ← can bloat result set with duplicates
 *
 * Best used when:
 *   - You have many parents but don't always need children
 *   - JOIN FETCH would produce too many duplicate rows (e.g., multiple bag collections)
 *
 * Watch the SQL log: with @BatchSize(10) and 20 departments,
 * you'll see Hibernate use IN (?, ?, ... ?) clauses instead of individual SELECTs.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BatchFetchService {

    private final DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    public void demonstrateBatchFetch() {
        log.info("=== Batch Fetch Demo ===");
        List<Department> departments = departmentRepository.findAll();

        // Accessing employees triggers batch loading — watch the SQL log
        // Instead of 1 SELECT per dept, Hibernate groups them:
        // SELECT * FROM employees WHERE department_id IN (1,2,3,...,10)
        // SELECT * FROM employees WHERE department_id IN (11,12,...,20)
        departments.forEach(d ->
            log.info("Dept '{}' → {} employees", d.getName(), d.getEmployees().size())
        );
    }
}
