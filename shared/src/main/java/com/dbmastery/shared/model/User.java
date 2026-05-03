package com.dbmastery.shared.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Canonical User model reused across all DB modules.
 * Each module maps this to its own storage as needed.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String role;
}
