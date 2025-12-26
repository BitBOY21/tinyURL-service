package com.tinyurl.tinyurl_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Model representing a shortened URL mapping.
 * Stored in MongoDB for high scalability and performance.
 */
@Document(collection = "short_urls") // Maps to MongoDB collection
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrl {

    @Id
    private String id; // MongoDB uses String (ObjectId) as default

    @Indexed(unique = true) // Crucial for performance: creates a unique index on the short key
    private String shortKey;

    private String originalUrl;

    private Long userId; // Reference to the user who created it (from PostgreSQL)

    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;
}