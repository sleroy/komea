package org.komea.event.storage.service;

import java.util.Date;

import org.komea.event.model.beans.BasicEvent;

public class NewBugzillaBugEvent extends BasicEvent {
	private int bugID; // bugID
	private int severity; // [0-5]

	public NewBugzillaBugEvent() {
		this.eventType = "new_bug";
		this.provider = "bugzilla";
		this.date = new Date();
	}

	public NewBugzillaBugEvent(final int _bugID, final int _severity) {
		this();
		this.bugID = _bugID;
		this.severity = _severity;
	}

	public int getBugID() {
		return bugID;
	}

	public void setBugID(int _bugID) {
		bugID = _bugID;
	}

	public int getSeverity() {
		return severity;
	}

	public void setSeverity(int _severity) {
		severity = _severity;
	}

}