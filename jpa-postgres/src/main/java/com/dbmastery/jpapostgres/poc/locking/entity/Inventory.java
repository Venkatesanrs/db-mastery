package com.dbmastery.jpapostgres.poc.locking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * POC: Optimistic Locking
 *
 * @Version adds a 'version' column to the table.
 * Hibernate increments it on every UPDATE.
 *
 * When two transactions read the same row (version=1) and both try to update:
 *   - First  commit → sets version=2, succeeds ✅
 *   - Second commit → WHERE version=1 matches nothing → throws OptimisticLockException ❌
 *
 * No DB-level lock is held between read and write — great for high-read, low-conflict scenarios.
 * Use pessimistic locking (SELECT FOR UPDATE) for high-conflict scenarios instead.
 */
@Entity
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    private Integer stockCount;

    private BigDecimal price;

    @Version   // ← Hibernate manages this; never set it manually
    private Long version;
}
