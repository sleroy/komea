package org.komea.event.storage.mysql.kryo.impl;

import java.io.ByteArrayOutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.google.common.base.Function;

/**
 * Created by Richard on 4/9/14.
 */
public class KryoObjectToByteArrayConverter<T> implements Function<T, byte[]> {
	private static final int	DEFAULT_SIZE	= 10000;
	private final Kryo	     kryo;
	private final Class<T>	 type;

	public KryoObjectToByteArrayConverter(final Kryo kryo, final Class<T> type) {
		this.kryo = kryo;
		this.type = type;
	}

	@Override
	public byte[] apply(final T value) {

		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final Output streamOut = new Output(baos, DEFAULT_SIZE);
		this.kryo.writeObject(streamOut, value);
		streamOut.close();
		return baos.toByteArray();
	}
}
