package org.komea.connectors.sdk.main.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionHandlerFilter;
import org.komea.connectors.sdk.main.ConnectorCommand;
import org.komea.connectors.sdk.main.IConnector;

public class Connector implements IConnector {

	private static final int	              DEFAULT_USAGE_WITH	= 80;
	// receives other command line parameters than options

	private final Map<String, CommandTrigger>	commands	     = new HashMap<>();

	@Argument(index = 0, usage = "Name of the command to execute", required = true)
	private String	                          action;

	private final String	                  connectorName;

	private boolean	                          hasParsed	         = false;

	public Connector(final String _connectorName) {
		super();
		this.connectorName = _connectorName;
	}

	/**
	 * Adds a command to the connector cli.
	 *
	 * @param _command
	 *            the command;
	 */
	public void addCommand(final Class<?> _command) {
		final CommandTrigger commandTrigger = new CommandTrigger();
		final ConnectorCommand annotation = _command.getAnnotation(ConnectorCommand.class);
		if (annotation == null) { throw new InvalidConnectorCommandException(_command); }
		commandTrigger.setAction(annotation.value());
		commandTrigger.setDescription(annotation.description());
		commandTrigger.setImpl(_command);
		this.commands.put(commandTrigger.getAction(), commandTrigger);
	}

	public boolean isHasParsed() {
		return this.hasParsed;
	}

	@Override
	public void run(final String... args) {
		final CmdLineParser parser = new CmdLineParser(this);
		// if you have a wider console, you could increase the value;
		// here 80 is also the default
		try {
			if (args.length == 0) { throw new CmdLineException(parser, "Missing action in argument"); }
			// parse the arguments.
			parser.parseArgument(args[0]);
			// you can parse additional arguments if you want.
			// parser.parseArgument("more","args");
			// after parsing arguments, you should check
			// if enough arguments are given.
			this.executeConnector(args);
			this.hasParsed = true;
		} catch (final CmdLineException e) {
			// if there's a problem in the command line,
			// you'll get this exception. this will report
			// an error message.
			System.err.println(e.getMessage());
			System.err.println("java " + this.connectorName + " [options...] arguments...");
			// print the list of available options
			parser.printUsage(System.err);
			System.err.println();
			// print option sample. This is useful some time
			System.err.println(" Example: java " + this.connectorName + " "
			        + parser.printExample(OptionHandlerFilter.ALL));
			return;
		}

	}

	protected void executeConnector(final String[] args) {

		final CommandTrigger commandTrigger = this.commands.get(this.action);
		if (commandTrigger == null) { throw new UnknownConnectorCommandException(
		        "Unknown action has been provided to the cli " + this.action); }

		final CmdLineParser parser = new CmdLineParser(commandTrigger.newCommand());
		try {
			parser.parseArgument(Arrays.copyOfRange(args, 1, args.length));
		} catch (final CmdLineException e) {
			System.err.println(e.getMessage());
			System.err.println("Command " + this.action + " [options...] arguments...");
			parser.printUsage(System.err);
			System.err.println();
			System.err.println(" Example: java " + this.connectorName + " " + this.action + " "
			        + parser.printExample(OptionHandlerFilter.ALL));
			throw new ConnectorCommandCliException(this.action, e);
		}

	}

}
