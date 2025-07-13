package com.priyakdey.droplet.service;

import com.priyakdey.droplet.security.payload.SessionPayload;
import org.springframework.http.ResponseCookie;

/**
 * @author Priyak Dey
 */
public interface CookieService {

    ResponseCookie create(SessionPayload payload);

}
