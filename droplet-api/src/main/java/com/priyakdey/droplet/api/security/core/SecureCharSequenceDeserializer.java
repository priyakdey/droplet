package com.priyakdey.droplet.api.security.core;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author Priyak Dey
 */
public class SecureCharSequenceDeserializer extends JsonDeserializer<CharSequence> {

    @Override
    public CharSequence deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        char[] buffer = p.getTextCharacters();
        int offset = p.getTextOffset();
        int length = p.getTextLength();
        return new SecureCharSequence(Arrays.copyOfRange(buffer, offset, length));
    }
}
