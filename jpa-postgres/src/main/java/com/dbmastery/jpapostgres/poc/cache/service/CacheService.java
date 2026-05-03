package com.dbmastery.jpapostgres.poc.cache.service;

import com.dbmastery.jpapostgres.poc.cache.entity.CatalogItem;
import com.dbmastery.jpapostgres.poc.cache.repository.CatalogItemRepository;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * POC: Second-Level Cache
 *
 * To observe caching in action, enable Hibernate statistics in application.yml:
 *   spring.jpa.properties.hibernate.generate_statistics: true
 *
 * Then watch logs for:
 *   - "second level cache puts"   → item stored in L2 cache
 *   - "second level cache hits"   → item served from L2 cache (no DB query)
 *   - "second level cache misses" → item not in cache, DB queried
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CacheService {

    private final CatalogItemRepository catalogItemRepository;
    private final EntityManagerFactory entityManagerFactory;

    @Transactional(readOnly = true)
    public CatalogItem findById(Long id) {
        log.info("Fetching CatalogItem id={} (check logs for cache hit/miss)", id);
        return catalogItemRepository.findById(id).orElseThrow();
    }

    @Transactional
    public CatalogItem save(CatalogItem item) {
        return catalogItemRepository.save(item);
    }

    /**
     * Prints Hibernate L2 cache statistics to the log.
     * Call this after a few findById() calls to see hit/miss counts.
     */
    public void printCacheStats() {
        Statistics stats = entityManagerFactory
                .unwrap(SessionFactory.class)
                .getStatistics();

        log.info("=== Hibernate L2 Cache Stats ===");
        log.info("Cache puts:   {}", stats.getSecondLevelCachePutCount());
        log.info("Cache hits:   {}", stats.getSecondLevelCacheHitCount());
        log.info("Cache misses: {}", stats.getSecondLevelCacheMissCount());
        log.info("DB queries:   {}", stats.getQueryExecutionCount());
    }

    /** Evict a single entity from the cache (e.g. after an update). */
    public void evict(Long id) {
        entityManagerFactory.getCache().evict(CatalogItem.class, id);
        log.info("Evicted CatalogItem id={} from L2 cache", id);
    }
}
