package com.priyakdey.droplet.security.payload;

import java.time.Instant;

/**
 * @author Priyak Dey
 */
public record SessionPayload(String issuer, Instant iat, Instant eat, String sub,
                             long expirationInSec) {
}
