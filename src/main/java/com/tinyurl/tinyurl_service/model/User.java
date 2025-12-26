package com.tinyurl.tinyurl_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity representing a user in the PostgreSQL database.
 * Managed by Spring Data JPA.
 */
@Entity
@Table(name = "users") // Maps this class to the 'users' table in Postgres
@Data                  // Lombok: Generates Getters, Setters, toString, equals, and hashCode
@NoArgsConstructor    // Lombok: Generates a no-argument constructor
@AllArgsConstructor   // Lombok: Generates a constructor with all fields
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * This method is called automatically before the entity is saved to the DB.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}