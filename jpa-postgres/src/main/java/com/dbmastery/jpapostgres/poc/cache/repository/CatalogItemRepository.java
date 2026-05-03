package com.dbmastery.jpapostgres.poc.cache.repository;

import com.dbmastery.jpapostgres.poc.cache.entity.CatalogItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import jakarta.persistence.QueryHint;
import java.util.List;

import static org.hibernate.jpa.HibernateHints.HINT_CACHEABLE;

@Repository
public interface CatalogItemRepository extends JpaRepository<CatalogItem, Long> {

    // Cache the query result set too (query cache)
    @QueryHints(@QueryHint(name = HINT_CACHEABLE, value = "true"))
    List<CatalogItem> findByCategory(String category);
}
