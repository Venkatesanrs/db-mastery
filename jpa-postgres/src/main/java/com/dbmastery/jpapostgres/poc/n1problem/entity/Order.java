package com.dbmastery.jpapostgres.poc.n1problem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * POC: N+1 Problem
 *
 * The classic trap: loading 100 orders fires 1 query for orders,
 * then 100 more queries (one per order) to load each order's items.
 * Total = N+1 queries.
 *
 * Fix options demonstrated in OrderN1Service:
 *   1. JOIN FETCH in JPQL
 *   2. @EntityGraph on repository method
 */
@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    private LocalDateTime orderDate;

    private BigDecimal totalAmount;

    // LAZY by default on OneToMany — this is what causes N+1
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();
}
