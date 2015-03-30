package org.komea.event.utils.kryo.impl;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.google.common.base.Function;

/**
 * Created by Richard on 4/9/14.
 */
public class KryoByteArrayToObjectConverter<T> implements Function<byte[], T> {

    /**
     * Kryo valueObjectConverter/valueSerializer
     */
    private final Kryo kryo;
    /**
     * Type of class you are reading/writing.
     */
    private final Class<T> type;

    public KryoByteArrayToObjectConverter(final Kryo kryo, final Class<T> type) {
        this.kryo = kryo;
        this.type = type;
    }

    @Override
    public T apply(final byte[] value) {
        if (value == null || value.length == 0) {
            return null;
        }
        Input input = new Input(value);
        return this.kryo.readObject(input, this.type);
    }
}
