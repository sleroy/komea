package org.komea.event.storage.impl;

import java.util.Date;

import org.komea.event.model.impl.KomeaEvent;

public class NewBugzillaBugEvent extends KomeaEvent {

    private int bugID;	  // bugID
    private int severity; // [0-5]

    public NewBugzillaBugEvent() {
        super("new_bug", "bugzilla", new Date());
    }

    public NewBugzillaBugEvent(final int _bugID, final int _severity) {
        this();
        this.bugID = _bugID;
        this.severity = _severity;
    }

    public int getBugID() {
        return this.bugID;
    }

    public int getSeverity() {
        return this.severity;
    }

    public void setBugID(final int _bugID) {
        this.bugID = _bugID;
    }

    public void setSeverity(final int _severity) {
        this.severity = _severity;
    }

}
