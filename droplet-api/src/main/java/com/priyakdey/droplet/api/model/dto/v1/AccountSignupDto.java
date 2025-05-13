package com.priyakdey.droplet.api.model.dto.v1;

/**
 * @author Priyak Dey
 */
public record AccountSignupDto(Integer id, String name, String email, String passwordHash) {

    public static AccountSignupDto from(String name, String email, String passwordHash) {
        return new AccountSignupDto(-1, name, email, passwordHash);
    }

}