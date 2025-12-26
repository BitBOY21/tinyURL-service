package com.tinyurl.tinyurl_service.repository;

import com.tinyurl.tinyurl_service.model.ShortUrl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for MongoDB operations on ShortUrl documents.
 */
@Repository
public interface ShortUrlRepository extends MongoRepository<ShortUrl, String> {

    /**
     * Finds a URL mapping by its short key.
     * This will use the unique index we defined in the model.
     */
    Optional<ShortUrl> findByShortKey(String shortKey);
}