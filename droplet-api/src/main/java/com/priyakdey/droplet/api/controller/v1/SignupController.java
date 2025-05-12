package com.priyakdey.droplet.api.controller.v1;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * @author Priyak Dey
 */
@RestController
@RequestMapping(path = "/v1/signup", consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE)
public class SignupController {

    @PostMapping
    public String signup() {
        return "Signup successful!";
    }

}
