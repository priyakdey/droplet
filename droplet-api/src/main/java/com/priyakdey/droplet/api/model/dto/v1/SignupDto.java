package com.priyakdey.droplet.api.model.dto.v1;

/**
 * @author Priyak Dey
 */
public record SignupDto(String name, String email, String password, String timezone) {

    public static SignupDto from(String name, String email, String password, String timezone) {
        return new SignupDto(name, email, password, timezone);
    }

}