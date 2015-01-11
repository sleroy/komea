package org.komea.event.utils.jackson.impl;

import java.io.ByteArrayInputStream;

import org.komea.event.storage.EventStorageException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;

/**
 * Created by Richard on 4/9/14.
 */
public class JacksonByteArrayToObjectConverter<T> implements
		Function<byte[], T> {
	/**
	 * Kryo valueObjectConverter/valueSerializer
	 */
	private final ObjectMapper jackson;
	private static final int DEFAULT_SIZE = 10000;
	/**
	 * Type of class you are reading/writing.
	 */
	private final Class<T> type;

	public JacksonByteArrayToObjectConverter(final ObjectMapper jackson,
			final Class<T> type) {
		this.jackson = jackson;
		this.type = type;
	}

	@Override
	public T apply(final byte[] value) {
		if (value == null || value.length == 0) {
			return null;
		}
		T v = null;

		try (final ByteArrayInputStream inputStream = new ByteArrayInputStream(
				value);) {
			v = this.jackson.readValue(inputStream, this.type);
		} catch (final Exception e) {
			throw new EventStorageException(e.getMessage(), e);
		}
		return v;

	}
}
