package com.priyakdey.droplet.api.model.dto.v1;

/**
 * @author Priyak Dey
 */
public record SignupDto(String name, String email, String password, String timeZone) {

    public static SignupDto from(String name, String email, String password, String timeZone) {
        return new SignupDto(name, email, password, timeZone);
    }

}