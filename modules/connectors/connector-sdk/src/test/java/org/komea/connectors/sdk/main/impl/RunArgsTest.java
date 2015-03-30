package org.komea.connectors.sdk.main.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RunArgsTest {

	@Test
	public void testRunArgs() throws Exception {
		assertEquals("Array is empty", 0, new RunArgs().asMainArgs().length);
		assertEquals("Array is empty", 0, RunArgs.newArgs().asMainArgs().length);
	}

	@Test
	public void testWithArg() throws Exception {
		assertEquals("Array as one arg", 1, new RunArgs().withArg("gni").asMainArgs().length);
		assertEquals("Array as two args", 2, new RunArgs().withArg("gni", "gni2").asMainArgs().length);
	}

}
