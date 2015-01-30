/**
 *
 */
package org.komea.connectors.sdk.bugtracker;

import java.io.Serializable;
import java.util.Map;

import org.komea.core.utils.PojoToMap;
import org.komea.event.model.impl.BasicEvent;

import com.google.common.collect.Maps;

/**
 * @author sleroy
 *
 */
public class BugEvent extends BasicEvent {
	private Map<String, Serializable> bug = Maps.newHashMap();
	private Map<String, Serializable> projectInfos = Maps.newHashMap();
	private String projectID;

	/**
	 * New bug event.
	 */
	public BugEvent(final String _provider, final String _eventType,
			final Object _bug, final Object _project, final String _projectID) {
		super();
		this.setProvider(_provider);
		this.setEventType(_eventType);
		if (_bug != null) {
			this.setBug(_bug);
		}
		if (_project != null) {
			this.setProjectInfos(_project);
		}
		this.projectID = _projectID;
	}

	/**
	 * @return the bug
	 */
	public Map<String, Serializable> getBug() {
		return this.bug;
	}

	/**
	 * @return the projectID
	 */
	public String getProjectID() {
		return this.projectID;
	}

	/**
	 * @return the projectInfos
	 */
	public Map<String, Serializable> getProjectInfos() {
		return this.projectInfos;
	}

	/**
	 * @param _bug
	 *            the bug to set
	 */
	public void setBug(final Map<String, Serializable> _bug) {
		this.bug = _bug;
	}

	public void setBug(final Object _bug) {
		this.bug = new PojoToMap().convertPojoInMap(_bug);
	}

	/**
	 * @param _projectID
	 *            the projectID to set
	 */
	public void setProjectID(final String _projectID) {
		this.projectID = _projectID;
	}

	/**
	 * @param _projectInfos
	 *            the projectInfos to set
	 */
	public void setProjectInfos(final Map<String, Serializable> _projectInfos) {
		this.projectInfos = _projectInfos;
	}

	public void setProjectInfos(final Object _project) {
		this.projectInfos = new PojoToMap().convertPojoInMap(_project);
	}

}
