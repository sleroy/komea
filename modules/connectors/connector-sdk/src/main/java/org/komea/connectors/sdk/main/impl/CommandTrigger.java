package org.komea.connectors.sdk.main.impl;

public class CommandTrigger {
	private String	 action;
	private String	 description;
	private Class<?>	impl;

	public String getAction() {
		return this.action;
	}

	public String getDescription() {
		return this.description;
	}

	public Object newCommand() {

		try {
			return this.impl.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public void setAction(final String _action) {
		this.action = _action;
	}

	public void setDescription(final String _description) {
		this.description = _description;
	}

	public void setImpl(final Class<?> _impl) {
		this.impl = _impl;
	}
}
