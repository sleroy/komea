/**
 *
 */
package org.komea.event.utils.jackson.impl;

import org.komea.events.serializer.jackson.JacksonObjectToByteArrayConverter;
import org.komea.events.serializer.jackson.JacksonByteArrayToObjectConverter;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.komea.events.dao.EventStorageException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author sleroy
 *
 */
public class JacksonByteArrayToObjectConverterTest {

	public static class A {
		DateTime c = new DateTime();
	}

	/**
	 * Test method for
	 * {@link org.komea.event.utils.jackson.impl.JacksonByteArrayToObjectConverter#apply(byte[])}
	 * .
	 */
	@Test
	public void testApply() throws Exception {

		final ObjectMapper jackson = new ObjectMapper();
		final JacksonObjectToByteArrayConverter<String> obs = new JacksonObjectToByteArrayConverter<>(
				jackson);
		final String argument = "salut";
		final byte[] byteArray0 = obs.apply(argument);
		final JacksonByteArrayToObjectConverter<String> jacksonByteArrayToObjectConverter = new JacksonByteArrayToObjectConverter<>(
				jackson, String.class);
		final String apply = jacksonByteArrayToObjectConverter
				.apply(byteArray0);
		Assert.assertEquals(argument, apply);
	}

	/**
	 * Test method for
	 * {@link org.komea.event.utils.jackson.impl.JacksonByteArrayToObjectConverter#apply(byte[])}
	 * .
	 */
	@Test
	public void testApplyNull() throws Exception {

		final ObjectMapper jackson = new ObjectMapper();
		final JacksonByteArrayToObjectConverter<String> obs = new JacksonByteArrayToObjectConverter<String>(
				jackson, String.class);

		Assert.assertEquals(null, obs.apply(null));
	}

	/**
	 * Test method for
	 * {@link org.komea.event.utils.jackson.impl.JacksonByteArrayToObjectConverter#apply(byte[])}
	 * .
	 */
	@Test(expected = EventStorageException.class)
	public void testApplyWithError() throws Exception {

		final ObjectMapper jackson = new ObjectMapper();
		final JacksonObjectToByteArrayConverter<A> obs = new JacksonObjectToByteArrayConverter<>(
				jackson);
		final A argument = new A();
		final byte[] byteArray0 = obs.apply(argument);
		final JacksonByteArrayToObjectConverter<A> jacksonByteArrayToObjectConverter = new JacksonByteArrayToObjectConverter<>(
				jackson, A.class);
		final A apply = jacksonByteArrayToObjectConverter.apply(byteArray0);
		Assert.assertEquals(argument, apply);
	}
}
