package com.tinyurl.tinyurl_service.controller;

import com.tinyurl.tinyurl_service.service.UrlService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * REST Controller for URL shortening and redirection.
 */
@RestController
@RequestMapping("/api/v1/urls")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    /**
     * Endpoint to create a shortened URL.
     * POST /api/v1/urls/shorten
     * * @param request Object containing the original URL
     * @return The shortened key
     */
    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody ShortenRequest request) {
        String shortKey = urlService.shortenUrl(request.getOriginalUrl());
        return new ResponseEntity<>(shortKey, HttpStatus.CREATED);
    }

    /**
     * Endpoint to redirect from a short key to the original URL.
     * GET /api/v1/urls/{shortKey}
     * * @param shortKey The key to resolve
     * @return A 302 Redirect to the original URL
     */
    @GetMapping("/{shortKey}")
    public ResponseEntity<Void> redirectToOriginal(@PathVariable String shortKey) {
        String originalUrl = urlService.getOriginalUrl(shortKey);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }

    /**
     * Simple DTO (Data Transfer Object) for the request body.
     */
    @Data
    public static class ShortenRequest {
        private String originalUrl;
    }
}