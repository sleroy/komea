package org.komea.events.serializer.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import org.komea.events.dao.EventStorageException;

public class JacksonObjectToByteArrayConverter<T> implements Function<T, byte[]> {

    private final ObjectMapper jackson;

    public JacksonObjectToByteArrayConverter(final ObjectMapper _jackson) {
        this.jackson = _jackson;
    }

    @Override
    public byte[] apply(final T value) {
        try {
            return this.jackson.writeValueAsBytes(value);
        } catch (final JsonProcessingException ex) {
            throw new EventStorageException(ex.getMessage(), ex);
        }
    }
}
