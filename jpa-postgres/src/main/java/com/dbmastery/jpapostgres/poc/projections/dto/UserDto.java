package com.dbmastery.jpapostgres.poc.projections.dto;

/**
 * POC: DTO (Class/Record) Projection
 *
 * A Java record used directly in JPQL constructor expressions.
 * SQL emitted: SELECT u.name, u.email, u.role FROM users u
 *
 * Advantages over interface projection:
 *   - Concrete type — can add methods, validation
 *   - Works well with Jackson serialization out of the box
 *   - No proxy overhead
 */
public record UserDto(String name, String email, String role) {}
