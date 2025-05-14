package com.priyakdey.droplet.api.service.v1;

import com.priyakdey.droplet.api.model.dto.v1.SignupDto;
import com.priyakdey.droplet.api.model.response.v1.SignupResponse;

/**
 * @author Priyak Dey
 */
public interface AuthenticationService {

    SignupResponse signup(SignupDto signupDto);

}
