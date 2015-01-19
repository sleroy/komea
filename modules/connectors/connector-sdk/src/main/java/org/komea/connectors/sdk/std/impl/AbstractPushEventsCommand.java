package org.komea.connectors.sdk.std.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;

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
			new ObjectMapper().writeValue(getLastLaunchFileName(), new Date());
		} catch (final IOException e) {
			LOGGER.error("Could not save the launch time {}", e.getMessage(), e);
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
		if (hasLastLaunchDate()) {
			return getSince();
		}
		final File lastLaunchFileName = getLastLaunchFileName();
		if (lastLaunchFileName.exists()) {
			try {
				return new DateTime(new ObjectMapper().readValue(
						lastLaunchFileName, Date.class));
			} catch (final IOException e) {
				LOGGER.error("Could not read the launch time {}",
						e.getMessage(), e);
			}
		}
		return null;

	}

	/**
	 * @param _eventoryClientA
	 * @throws Exception
	 */
	@Override
	protected final void runCommand(final IEventoryClientAPI _eventoryClientAPI)
			throws Exception {
		final DateTime readLastLaunchTime = readLastLaunchTime();
		try {
			sendEvents(_eventoryClientAPI, readLastLaunchTime);
		} finally {
			saveActionDate();
		}
	}

	/**
	 * @param _eventoryClientAPI
	 * @throws Exception
	 */
	protected abstract void sendEvents(IEventoryClientAPI _eventoryClientAPI,
			final DateTime _lastExecution) throws Exception;

}
