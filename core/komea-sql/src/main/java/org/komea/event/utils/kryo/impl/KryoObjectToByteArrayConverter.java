package org.komea.event.utils.kryo.impl;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.google.common.base.Function;

/**
 * Created by Richard on 4/9/14.
 */
public class KryoObjectToByteArrayConverter<T> implements Function<T, byte[]> {

    private static final int DEFAULT_SIZE = 1024;
    private final Kryo kryo;

    public KryoObjectToByteArrayConverter(final Kryo kryo) {
        this.kryo = kryo;
    }

    @Override
    public byte[] apply(final T value) {
        final Output streamOut = new Output(DEFAULT_SIZE);
        this.kryo.writeObject(streamOut, value);
        return streamOut.toBytes();
    }
}
