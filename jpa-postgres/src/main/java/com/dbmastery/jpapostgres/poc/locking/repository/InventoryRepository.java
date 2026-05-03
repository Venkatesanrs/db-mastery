package com.dbmastery.jpapostgres.poc.locking.repository;

import com.dbmastery.jpapostgres.poc.locking.entity.Inventory;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    // Optimistic lock — just a regular find; @Version handles conflict detection
    Optional<Inventory> findById(Long id);

    // Pessimistic lock alternative — SELECT FOR UPDATE (blocks other writers)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT i FROM Inventory i WHERE i.id = :id")
    Optional<Inventory> findByIdWithPessimisticLock(Long id);
}
