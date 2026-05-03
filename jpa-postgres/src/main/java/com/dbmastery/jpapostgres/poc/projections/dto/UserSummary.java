package com.dbmastery.jpapostgres.poc.projections.dto;

/**
 * POC: Interface Projection
 *
 * Spring Data generates a proxy at runtime that maps query columns to these getters.
 * SQL emitted: SELECT name, email FROM users   (not SELECT *)
 *
 * Use when: you need a subset of columns as a lightweight read model.
 */
public interface UserSummary {
    String getName();
    String getEmail();
}
