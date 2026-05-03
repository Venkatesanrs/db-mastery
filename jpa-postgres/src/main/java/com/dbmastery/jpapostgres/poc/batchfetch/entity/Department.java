package com.dbmastery.jpapostgres.poc.batchfetch.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

/**
 * POC: Batch Fetching
 *
 * @BatchSize(size = 10) tells Hibernate: when you need to load
 * the employees collection, load up to 10 at a time using an IN clause
 * instead of one query per department.
 *
 * Without @BatchSize + 20 departments → 21 queries (N+1)
 * With    @BatchSize(10) + 20 departments → 3 queries (1 + ceil(20/10))
 */
@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String location;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @BatchSize(size = 10)   // ← the magic: batch load employees in groups of 10
    @Builder.Default
    private List<Employee> employees = new ArrayList<>();
}
