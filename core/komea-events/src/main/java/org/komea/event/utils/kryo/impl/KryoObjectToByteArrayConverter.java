package org.komea.event.utils.kryo.impl;

import java.io.ByteArrayOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.google.common.base.Function;

/**
 * Created by Richard on 4/9/14.
 */
public class KryoObjectToByteArrayConverter<T> implements Function<T, byte[]> {
	private final Kryo kryo;
	private final Class<T> type;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(KryoObjectToByteArrayConverter.class);
	private static final int DEFAULT_SIZE = 100000;

	public KryoObjectToByteArrayConverter(final Kryo kryo, final Class<T> _type) {
		this.kryo = kryo;
		this.type = _type;
	}

	@Override
	public byte[] apply(final T value) {

		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		final Output streamOut = new Output(outputStream, DEFAULT_SIZE);
		this.kryo.writeObject(streamOut, value);
		streamOut.close();
		return outputStream.toByteArray();
	}
}
