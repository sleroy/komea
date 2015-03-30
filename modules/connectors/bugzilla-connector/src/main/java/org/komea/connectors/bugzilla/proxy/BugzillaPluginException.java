package org.komea.connectors.bugzilla.proxy;

public class BugzillaPluginException extends RuntimeException {
	private static final long serialVersionUID = -4618924896412808735L;

	public BugzillaPluginException(final Throwable _ex) {
		super("Bugzilla connector meets an exception : ", _ex);
	}
}
