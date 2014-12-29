package org.komea.microservices.events.mq;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.FileSystemUtils;

public class Receiver {

	/**
	 * Get a copy of the application context
	 */
	@Autowired
	ConfigurableApplicationContext	context;

	/**
	 * When you receive a message, print it out, then shut down the application.
	 * Finally, clean up any ActiveMQ server stuff.
	 */
	public void receiveMessage(final String message) {
		System.out.println("Received <" + message + ">");
		this.context.close();
		FileSystemUtils.deleteRecursively(new File("activemq-data"));
	}
}