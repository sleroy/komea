package org.komea.event.utils.jackson.impl;

import java.io.IOException;

import org.komea.event.storage.EventStorageException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;

public class JacksonByteArrayToObjectConverter<T> implements
		Function<byte[], T> {

	private final ObjectMapper jackson;
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
		try {
			return this.jackson.readValue(value, this.type);
		} catch (final IOException ex) {
			throw new EventStorageException(ex.getMessage(), ex);
		}
	}
}
