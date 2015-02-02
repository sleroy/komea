package org.komea.event.utils.jackson.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.komea.event.storage.EventStorageException;

public class JacksonObjectToByteArrayConverter<T> implements Function<T, byte[]> {

    private final ObjectMapper jackson;

    public JacksonObjectToByteArrayConverter(final ObjectMapper _jackson) {
        this.jackson = _jackson;
    }

    @Override
    public byte[] apply(final T value) {
        try {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
            this.jackson.writeValue(baos, value);
            return baos.toByteArray();
        } catch (final IOException ex) {
            throw new EventStorageException(ex.getMessage(), ex);
        }
    }
}
