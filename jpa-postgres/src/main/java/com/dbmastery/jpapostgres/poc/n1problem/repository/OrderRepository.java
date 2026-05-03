package com.dbmastery.jpapostgres.poc.n1problem.repository;

import com.dbmastery.jpapostgres.poc.n1problem.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // ❌ BAD: triggers N+1 — items loaded lazily per order
    List<Order> findAll();

    // ✅ FIX 1: JOIN FETCH — single query with JOIN
    @Query("SELECT DISTINCT o FROM Order o JOIN FETCH o.items")
    List<Order> findAllWithItemsJoinFetch();

    // ✅ FIX 2: @EntityGraph — declarative, no JPQL needed
    @EntityGraph(attributePaths = {"items"})
    List<Order> findAllWithEntityGraph();
}
