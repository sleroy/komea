package org.komea.connectors.sdk.std.impl;

import java.io.File;
import java.io.IOException;

import org.joda.time.DateTime;
import org.komea.connectors.sdk.rest.impl.IEventoryClientAPI;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractPushEventsCommand extends AbstractEventoryCommand {

	public AbstractPushEventsCommand() {
		super();

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.komea.connectors.sdk.main.IConnectorCommand#action()
	 */
	@Override
	public String action() {

		return "push";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.komea.connectors.sdk.main.IConnectorCommand#description()
	 */
	@Override
	public String description() {
		return "Push events to the server";
	}

	public void saveActionDate() {
		try {
			new ObjectMapper().writeValue(getLastLaunchFileName(),
					new DateTime());
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Returns the last launch file name
	 *
	 * @return
	 */
	private File getLastLaunchFileName() {

		return new File("LAST_PUSH");
	}

	/**
	 * Read the last launch time
	 *
	 * @return
	 */
	private DateTime readLastLaunchTime() {
		final File lastLaunchFileName = getLastLaunchFileName();
		if (lastLaunchFileName.exists()) {
			try {
				return new ObjectMapper().readValue(lastLaunchFileName,
						DateTime.class);
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		return null;

	}

	/**
	 * @param _eventoryClientAPI
	 */
	@Override
	protected final void runCommand(final IEventoryClientAPI _eventoryClientAPI) {
		final DateTime readLastLaunchTime = readLastLaunchTime();
		try {
			sendEvents(_eventoryClientAPI, readLastLaunchTime);
		} finally {
			saveActionDate();
		}
	}

	/**
	 * @param _eventoryClientAPI
	 */
	protected abstract void sendEvents(IEventoryClientAPI _eventoryClientAPI,
			final DateTime _lastExecution);

}
