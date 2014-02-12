
package org.komea.product.backend.esper.test.factories;



import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.komea.product.backend.esper.test.DSLEventBuilder;
import org.komea.product.backend.esper.test.EventFactoryBuilder;



public class BugZillaEventFactory
{
    
    
    public static void main(final String[] args) throws IOException {
    
    
        final EventFactoryBuilder eventFactoryBuilder =
                new EventFactoryBuilder("BugZillaEventFactory", "org.komea.event.factory",
                        new File("src/test/java"));
        eventFactoryBuilder.register(DSLEventBuilder
                .newEvent("sendTotalBugs", "bugzilla", "bugzilla_total_bugs").linkToProject()
                .withValue().predefinedMessage("Total bugs for the project $project").build());
        eventFactoryBuilder.register(DSLEventBuilder
                .newEvent("sendUnconfirmedBugs", "bugzilla", "bugzilla_unconfirmed_bugs")
                .linkToProject().withValue().build());
        eventFactoryBuilder.register(DSLEventBuilder
                .newEvent("sendNewBugs", "bugzilla", "bugzilla_new_bugs").linkToProject()
                .withValue().build());
        eventFactoryBuilder.register(DSLEventBuilder
                .newEvent("sendAssignedBugs", "bugzilla", "bugzilla_assigned_bugs").linkToProject()
                .withValue().build());
        eventFactoryBuilder.register(DSLEventBuilder
                .newEvent("sendReopenedBugs", "bugzilla", "bugzilla_reopened_bugs").linkToProject()
                .withValue().build());
        eventFactoryBuilder.register(DSLEventBuilder
                .newEvent("sendReadyBugs", "bugzilla", "bugzilla_ready_bugs").linkToProject()
                .withValue().build());
        eventFactoryBuilder.register(DSLEventBuilder
                .newEvent("sendResolvedBugs", "bugzilla", "bugzilla_resolved_bugs").linkToProject()
                .withValue().build());
        
        
        final Map<String, Class<?>> properties = new HashMap<String, Class<?>>();
        properties.put("ticketName", String.class);
        properties.put("ticketUrl", String.class);
        properties.put("ticketStatus", String.class);
        
        
        eventFactoryBuilder.register(DSLEventBuilder
                .newEvent("sendNewTicketBug", "bugzilla",
                        "bugzilla_new_bug_ticket ${properties[ticketName]}.").linkToProject()
                .withValue().withParameters(properties).build());
        eventFactoryBuilder.register(DSLEventBuilder
                .newEvent("sendUpdatedTicketBug", "bugzilla",
                        "bugzilla_update_bug_ticket ${properties[ticketName]}.").linkToProject()
                .withValue().withParameters(properties).build());
        eventFactoryBuilder.register(DSLEventBuilder
                .newEvent("sendReminderTicketBug", "bugzilla",
                        "bugzilla_reminder_bug_ticket ${properties[ticketName]}.").linkToProject()
                .withValue().withParameters(properties).build());
        eventFactoryBuilder.generate();
    }
    
    
    public BugZillaEventFactory() {
    
    
        super();
        
    }
}
