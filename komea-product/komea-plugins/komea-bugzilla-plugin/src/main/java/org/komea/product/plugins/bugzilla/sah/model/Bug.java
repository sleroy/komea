package org.komea.product.plugins.bugzilla.sah.model;

import java.io.Serializable;

public class Bug implements Serializable {

    private static final long serialVersionUID = 1L;

    private BugSeverity severity;
    private BugPriority priority;
    private BugStatus status;

    public Bug() {
    }

    public Bug(BugSeverity severity, BugPriority priority, BugStatus status) {
        this.severity = severity;
        this.priority = priority;
        this.status = status;
    }

    public BugSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(BugSeverity severity) {
        this.severity = severity;
    }

    public BugPriority getPriority() {
        return priority;
    }

    public void setPriority(BugPriority priority) {
        this.priority = priority;
    }

    public BugStatus getStatus() {
        return status;
    }

    public void setStatus(BugStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Bug{" + "severity=" + severity + ", priority=" + priority + ", status=" + status + '}';
    }

}
