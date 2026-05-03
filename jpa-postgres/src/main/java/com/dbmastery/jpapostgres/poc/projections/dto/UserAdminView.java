package com.dbmastery.jpapostgres.poc.projections.dto;

import java.time.LocalDateTime;

/**
 * POC: Dynamic Projection target — richer view for admin use cases.
 * The same repository method returns this or UserSummary depending on caller.
 */
public interface UserAdminView {
    Long getId();
    String getName();
    String getEmail();
    String getRole();
    LocalDateTime getCreatedAt();
}
