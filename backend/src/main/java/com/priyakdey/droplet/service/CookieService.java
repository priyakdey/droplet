package com.priyakdey.droplet.service;

import com.priyakdey.droplet.security.SessionPayload;
import org.springframework.http.ResponseCookie;

/**
 * @author Priyak Dey
 */
public interface CookieService {

    ResponseCookie getCookie(SessionPayload payload);

}
