package com.priyakdey.droplet.api.security.hasher;

import com.priyakdey.droplet.api.security.core.SecureCharSequence;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * @author Priyak Dey
 */
public class SecureBCryptPasswordEncoder implements PasswordEncoder {

    private Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2(a|y|b)?\\$(\\d\\d)\\$[./0-9A-Za-z]{53}");

    private final int strength;

    private final BCryptPasswordEncoder.BCryptVersion version;

    private final SecureRandom secureRandom;

    public SecureBCryptPasswordEncoder(int strength, BCryptPasswordEncoder.BCryptVersion version,
                                       SecureRandom secureRandom) {
        this.strength = strength;
        this.version = version;
        this.secureRandom = secureRandom;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        if (!(rawPassword instanceof SecureCharSequence charSequence)) {
            throw new IllegalArgumentException("Expected type SecureCharSequence");
        }

        char[] data = charSequence.getData();
        byte[] buffer = toByteArray(data);

        String salt = BCrypt.gensalt(this.version.getVersion(), this.strength, this.secureRandom);
        String hashpw = BCrypt.hashpw(buffer, salt);
        Arrays.fill(buffer, (byte) 0x0);
        return hashpw;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (encodedPassword == null || encodedPassword.isBlank()
                || !BCRYPT_PATTERN.matcher(encodedPassword).matches()) {
            return false;
        }

        if (!(rawPassword instanceof SecureCharSequence charSequence)) {
            throw new IllegalArgumentException("Expected type SecureCharSequence");
        }

        char[] data = charSequence.getData();
        byte[] buffer = toByteArray(data);

        boolean isMatch = BCrypt.checkpw(buffer, encodedPassword);
        Arrays.fill(buffer, (byte) 0x0);

        return isMatch;
    }

    private byte[] toByteArray(char[] data) {
        CharBuffer cb = CharBuffer.wrap(data);
        ByteBuffer bb = StandardCharsets.UTF_8.encode(cb);
        return Arrays.copyOfRange(bb.array(), bb.position(), bb.limit());
    }

}
