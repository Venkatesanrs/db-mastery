package com.dbmastery.jpapostgres.poc.n1problem.service;

import com.dbmastery.jpapostgres.poc.n1problem.entity.Order;
import com.dbmastery.jpapostgres.poc.n1problem.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * POC: N+1 Problem — bad vs good approaches side by side.
 *
 * Enable SQL logging in application.yml to watch query counts:
 *   logging.level.org.hibernate.SQL: DEBUG
 *
 * With 10 orders:
 *   badFindAll()              → 11 queries  (1 + 10)
 *   findAllWithJoinFetch()    →  1 query
 *   findAllWithEntityGraph()  →  1 query
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderN1Service {

    private final OrderRepository orderRepository;

    /**
     * ❌ N+1 in action — watch the SQL log explode.
     * Each call to order.getItems() triggers a separate SELECT.
     */
    @Transactional(readOnly = true)
    public void badFindAll() {
        log.warn("=== BAD: N+1 approach ===");
        List<Order> orders = orderRepository.findAll();
        orders.forEach(o -> log.info("Order {} has {} items",
                o.getId(), o.getItems().size())); // lazy load fires here
    }

    /**
     * ✅ Fix 1: JOIN FETCH — Hibernate emits one SQL JOIN query.
     */
    @Transactional(readOnly = true)
    public void findAllWithJoinFetch() {
        log.info("=== GOOD: JOIN FETCH approach ===");
        List<Order> orders = orderRepository.findAllWithItemsJoinFetch();
        orders.forEach(o -> log.info("Order {} has {} items",
                o.getId(), o.getItems().size()));
    }

    /**
     * ✅ Fix 2: @EntityGraph — same result, no JPQL needed.
     * Preferred when you want to keep the query method clean.
     */
    @Transactional(readOnly = true)
    public void findAllWithEntityGraph() {
        log.info("=== GOOD: @EntityGraph approach ===");
        List<Order> orders = orderRepository.findAllWithEntityGraph();
        orders.forEach(o -> log.info("Order {} has {} items",
                o.getId(), o.getItems().size()));
    }
}
