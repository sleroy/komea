package org.komea.connectors.sdk.main.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * This class is a helper to build command line arguments.
 *
 * @author sleroy
 *
 */
public class RunArgs {

	public static RunArgs newArgs(final String... _args) {

		final RunArgs runArgs = new RunArgs(Lists.newArrayList(_args));

		return runArgs;
	}

	private final List<String>	arguments;

	public RunArgs() {
		this(new ArrayList<String>());
	}

	public RunArgs(final List<String> _newArrayList) {
		this.arguments = _newArrayList;
	}

	public String[] asMainArgs() {
		return this.arguments.toArray(new String[this.arguments.size()]);
	}

	public RunArgs withArg(final String _arg, final String... parameters) {
		this.arguments.add(_arg);
		this.arguments.addAll(Lists.newArrayList(parameters));
		return this;
	}
}
