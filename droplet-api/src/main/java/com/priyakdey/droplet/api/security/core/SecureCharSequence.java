package com.priyakdey.droplet.api.security.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Priyak Dey
 */
@SuppressWarnings("NullableProblems")
@JsonDeserialize(using = SecureCharSequenceDeserializer.class)
public class SecureCharSequence implements CharSequence, Serializable {
    @Serial
    private static final long serialVersionUID = -3178953603947966596L;

    private char[] data;

    public SecureCharSequence() {
    }

    public SecureCharSequence(char[] data) {
        this.data = data;
    }

    public char[] getData() {
        return data;
    }

    public void setData(char[] data) {
        this.data = data;
    }

    @Override
    public int length() {
        return data.length;
    }

    @Override
    public char charAt(int index) {
        if (index < 0 || index >= data.length) {
            throw new IllegalArgumentException("Invalid input");
        }
        return data[index];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        if (start < 0 || start >= data.length || end < 0 || end >= data.length || start > end) {
            throw new IllegalArgumentException("Invalid input");
        }
        return new SecureCharSequence(Arrays.copyOfRange(data, start, end));
    }

    @Override
    public boolean isEmpty() {
        return length() == 0;
    }

    public void clear() {
        Arrays.fill(data, '\0');
    }

    @Override
    public String toString() {
        return "";
    }

}
