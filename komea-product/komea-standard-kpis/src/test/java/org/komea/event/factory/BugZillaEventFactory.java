
package org.komea.event.factory;



import org.komea.product.database.dto.EventSimpleDto;



/**
 */
public class BugZillaEventFactory
{
    
    
    /**
     * Method sendAssignedBugs.
     * @param _projectName String
     * @param _value double
     * @return EventSimpleDto
     */
    public EventSimpleDto sendAssignedBugs(final String _projectName, final double _value) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProject(_projectName);
        event.setValue(_value);
        return event;
    }
    
    
    /**
     * Method sendNewBugs.
     * @param _projectName String
     * @param _value double
     * @return EventSimpleDto
     */
    public EventSimpleDto sendNewBugs(final String _projectName, final double _value) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProject(_projectName);
        event.setValue(_value);
        return event;
    }
    
    
    /**
     * Method sendNewTicketBug.
     * @param _projectName String
     * @param _value double
     * @param _ticketName java.lang.String
     * @param _ticketUrl java.lang.String
     * @param _ticketStatus java.lang.String
     * @return EventSimpleDto
     */
    public EventSimpleDto sendNewTicketBug(
            final String _projectName,
            final double _value,
            final java.lang.String _ticketName,
            final java.lang.String _ticketUrl,
            final java.lang.String _ticketStatus) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProject(_projectName);
        event.setValue(_value);
        event.setProperties(new java.util.HashMap());
        event.getProperties().put("ticketName", _ticketName);
        event.getProperties().put("ticketUrl", _ticketUrl);
        event.getProperties().put("ticketStatus", _ticketStatus);
        return event;
    }
    
    
    /**
     * Method sendReadyBugs.
     * @param _projectName String
     * @param _value double
     * @return EventSimpleDto
     */
    public EventSimpleDto sendReadyBugs(final String _projectName, final double _value) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProject(_projectName);
        event.setValue(_value);
        return event;
    }
    
    
    /**
     * Method sendReminderTicketBug.
     * @param _projectName String
     * @param _value double
     * @param _ticketName java.lang.String
     * @param _ticketUrl java.lang.String
     * @param _ticketStatus java.lang.String
     * @return EventSimpleDto
     */
    public EventSimpleDto sendReminderTicketBug(
            final String _projectName,
            final double _value,
            final java.lang.String _ticketName,
            final java.lang.String _ticketUrl,
            final java.lang.String _ticketStatus) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProject(_projectName);
        event.setValue(_value);
        event.setProperties(new java.util.HashMap());
        event.getProperties().put("ticketName", _ticketName);
        event.getProperties().put("ticketUrl", _ticketUrl);
        event.getProperties().put("ticketStatus", _ticketStatus);
        return event;
    }
    
    
    /**
     * Method sendReopenedBugs.
     * @param _projectName String
     * @param _value double
     * @return EventSimpleDto
     */
    public EventSimpleDto sendReopenedBugs(final String _projectName, final double _value) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProject(_projectName);
        event.setValue(_value);
        return event;
    }
    
    
    /**
     * Method sendResolvedBugs.
     * @param _projectName String
     * @param _value double
     * @return EventSimpleDto
     */
    public EventSimpleDto sendResolvedBugs(final String _projectName, final double _value) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProject(_projectName);
        event.setValue(_value);
        return event;
    }
    
    
    /**
     * Method sendTotalBugs.
     * @param _projectName String
     * @param _value double
     * @return EventSimpleDto
     */
    public EventSimpleDto sendTotalBugs(final String _projectName, final double _value) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProject(_projectName);
        event.setMessage("Total bugs for the project $project");
        event.setValue(_value);
        return event;
    }
    
    
    /**
     * Method sendUnconfirmedBugs.
     * @param _projectName String
     * @param _value double
     * @return EventSimpleDto
     */
    public EventSimpleDto sendUnconfirmedBugs(final String _projectName, final double _value) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProject(_projectName);
        event.setValue(_value);
        return event;
    }
    
    
    /**
     * Method sendUpdateTicketBug.
     * @param _projectName String
     * @param _value double
     * @param _ticketName java.lang.String
     * @param _ticketUrl java.lang.String
     * @param _ticketStatus java.lang.String
     * @return EventSimpleDto
     */
    public EventSimpleDto sendUpdateTicketBug(
            final String _projectName,
            final double _value,
            final java.lang.String _ticketName,
            final java.lang.String _ticketUrl,
            final java.lang.String _ticketStatus) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProject(_projectName);
        event.setValue(_value);
        event.setProperties(new java.util.HashMap());
        event.getProperties().put("ticketName", _ticketName);
        event.getProperties().put("ticketUrl", _ticketUrl);
        event.getProperties().put("ticketStatus", _ticketStatus);
        return event;
    }
    
}
