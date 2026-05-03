package com.dbmastery.jpapostgres.poc.cache.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.math.BigDecimal;

/**
 * POC: Hibernate Second-Level Cache (L2)
 *
 * L1 cache = per-Session (EntityManager) — automatic, always on.
 * L2 cache = shared across Sessions — opt-in, needs a cache provider.
 *
 * Here we use Caffeine (via hibernate-jcache) as the cache provider.
 *
 * @Cache(usage = READ_WRITE) → safe for entities that are updated occasionally.
 * Other strategies:
 *   READ_ONLY        → immutable data (fastest)
 *   NONSTRICT_READ_WRITE → eventual consistency (no locking)
 *   TRANSACTIONAL    → full transaction support (needs JTA)
 *
 * Result: Second findById() for the same ID skips the DB entirely.
 */
@Entity
@Table(name = "catalog_items")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "catalogItemCache")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CatalogItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String category;

    private BigDecimal price;

    private String description;
}
