package com.priyakdey.droplet.security.util;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import reactor.util.annotation.NonNull;

import java.util.Base64;

/**
 * @author Priyak Dey
 */
@Component
@ConfigurationPropertiesBinding
public class Base64ToByteArrayConverter implements Converter<String, byte[]> {

    @Override
    public byte[] convert(@NonNull String source) {
        return Base64.getDecoder().decode(source);
    }
}
