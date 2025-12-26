package com.tinyurl.tinyurl_service.repository;

import com.tinyurl.tinyurl_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for User entity operations.
 * Extends JpaRepository to get standard CRUD operations (Save, Delete, Find) out of the box.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by their username.
     * Spring Data JPA will automatically generate the SQL: SELECT * FROM users WHERE username = ?
     *
     * @param username the username to search for
     * @return an Optional containing the user if found, or empty if not
     */
    Optional<User> findByUsername(String username);

    /**
     * Check if a user exists with a specific email.
     *
     * @param email the email to check
     * @return true if exists, false otherwise
     */
    boolean existsByEmail(String email);
}