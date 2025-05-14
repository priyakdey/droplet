package com.priyakdey.droplet.api.service.v1;

import com.priyakdey.droplet.api.model.dto.v1.SignupDto;
import com.priyakdey.droplet.api.model.response.v1.AuthResponse;
import com.priyakdey.droplet.api.security.core.SecureCharSequence;

/**
 * @author Priyak Dey
 */
public interface AuthenticationService {

    AuthResponse signup(SignupDto signupDto);

    AuthResponse authenticate(String email, SecureCharSequence rawPassword);

}
