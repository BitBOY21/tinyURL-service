package com.tinyurl.tinyurl_service.service;

import org.springframework.stereotype.Service;

/**
 * Service responsible for encoding numerical IDs into Base62 strings
 * and decoding them back. This is the core logic of the URL shortener.
 */
@Service
public class Base62Service {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = CHARACTERS.length();

    /**
     * Converts a long numeric ID into a Base62 string.
     * * @param input The database ID to encode
     * @return A short Base62 string
     */
    public String encode(long input) {
        StringBuilder encodedString = new StringBuilder();

        if (input == 0) {
            return String.valueOf(CHARACTERS.charAt(0));
        }

        while (input > 0) {
            int remainder = (int) (input % BASE);
            encodedString.append(CHARACTERS.charAt(remainder));
            input /= BASE;
        }

        // The logic builds the string in reverse, so we flip it back
        return encodedString.reverse().toString();
    }

    /**
     * Converts a Base62 string back into its original numeric ID.
     * * @param input The short string to decode
     * @return The original long ID
     */
    public long decode(String input) {
        long decoded = 0;
        for (int i = 0; i < input.length(); i++) {
            int charIndex = CHARACTERS.indexOf(input.charAt(i));
            decoded = decoded * BASE + charIndex;
        }
        return decoded;
    }
}