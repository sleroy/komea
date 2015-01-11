package org.komea.event.utils.jackson.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;

/**
 * Created by Richard on 4/9/14.
 */
public class JacksonObjectToByteArrayConverter<T> implements
Function<T, byte[]> {
	private static final int DEFAULT_SIZE = 10000;
	private final ObjectMapper jackson;
	private final Class<T> type;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(JacksonObjectToByteArrayConverter.class);

	public JacksonObjectToByteArrayConverter(final ObjectMapper _jackson,
			final Class<T> type) {
		this.jackson = _jackson;
		this.type = type;
	}

	@Override
	public byte[] apply(final T value) {

		try (final ByteArrayOutputStream baos = new ByteArrayOutputStream(10000);) {
			this.jackson.writeValue(baos, value);
			return baos.toByteArray();
		} catch (final IOException e) {
			LOGGER.error(e.getMessage(), e);
		}

		return new byte[0];
	}
}
