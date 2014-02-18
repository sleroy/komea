
package org.komea.event.factory;



import org.komea.product.database.dto.EventSimpleDto;



public class BugZillaEventFactory
{
    
    
    public EventSimpleDto sendAssignedBugs(final String _projectName, final double _value) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProject(_projectName);
        event.setValue(_value);
        return event;
    }
    
    
    public EventSimpleDto sendNewBugs(final String _projectName, final double _value) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProject(_projectName);
        event.setValue(_value);
        return event;
    }
    
    
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
    
    
    public EventSimpleDto sendReadyBugs(final String _projectName, final double _value) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProject(_projectName);
        event.setValue(_value);
        return event;
    }
    
    
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
    
    
    public EventSimpleDto sendReopenedBugs(final String _projectName, final double _value) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProject(_projectName);
        event.setValue(_value);
        return event;
    }
    
    
    public EventSimpleDto sendResolvedBugs(final String _projectName, final double _value) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProject(_projectName);
        event.setValue(_value);
        return event;
    }
    
    
    public EventSimpleDto sendTotalBugs(final String _projectName, final double _value) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProject(_projectName);
        event.setMessage("Total bugs for the project $project");
        event.setValue(_value);
        return event;
    }
    
    
    public EventSimpleDto sendUnconfirmedBugs(final String _projectName, final double _value) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProject(_projectName);
        event.setValue(_value);
        return event;
    }
    
    
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
