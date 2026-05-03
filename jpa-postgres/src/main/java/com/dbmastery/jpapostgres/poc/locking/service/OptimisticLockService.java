package com.dbmastery.jpapostgres.poc.locking.service;

import com.dbmastery.jpapostgres.poc.locking.entity.Inventory;
import com.dbmastery.jpapostgres.poc.locking.repository.InventoryRepository;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * POC: Optimistic Locking with retry.
 *
 * Pattern:
 *   1. Read entity (includes current version)
 *   2. Modify in memory
 *   3. Save → Hibernate checks version in WHERE clause
 *   4. If stale → OptimisticLockException → retry or surface to user
 *
 * To simulate a conflict:
 *   - Call decreaseStock() from two threads simultaneously with the same id
 *   - One will succeed, the other will throw → retry kicks in
 *
 * Enable spring-retry in your Application class:
 *   @EnableRetry
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OptimisticLockService {

    private final InventoryRepository inventoryRepository;

    /**
     * Decrease stock safely with optimistic locking + automatic retry.
     * Spring Retry retries up to 3 times with 100ms backoff on conflict.
     */
    @Retryable(
        retryFor = { ObjectOptimisticLockingFailureException.class, OptimisticLockException.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 100)
    )
    @Transactional
    public Inventory decreaseStock(Long id, int quantity) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found: " + id));

        if (inventory.getStockCount() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        inventory.setStockCount(inventory.getStockCount() - quantity);
        log.info("Decreasing stock for '{}': {} → {} (version={})",
                inventory.getProductName(),
                inventory.getStockCount() + quantity,
                inventory.getStockCount(),
                inventory.getVersion());

        return inventoryRepository.save(inventory);
        // If another transaction committed between our read and this save,
        // Hibernate throws OptimisticLockException → @Retryable re-reads and retries
    }

    @Transactional
    public Inventory create(String productName, int stock, java.math.BigDecimal price) {
        return inventoryRepository.save(
            Inventory.builder()
                .productName(productName)
                .stockCount(stock)
                .price(price)
                .build()
        );
    }
}
