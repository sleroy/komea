
package org.komea.product.backend.esper.test.factories;



import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Provider;



public class EsperEventFactory
{
    
    
    public static Provider  SONAR_PROVIDER                  = sonarProvider();
    
    
    public static Provider  TESTLINK_PROVIDER               = testLinkProvider();
    
    
    public static Provider  JENKINS_PROVIDER                = jenkinsProvider();
    
    
    public static Provider  PERFORCE_PROVIDER               = perforceProvider();
    
    
    public static Provider  BUGZILLA_PROVIDER               = bugzillaProvider();
    
    public static EventType EVENT_BUGZILLA_TOTAL_BUGS       = newEventType("bugzilla_total_bugs",
                                                                    Severity.INFO);
    
    public static EventType EVENT_BUGZILLA_UNCONFIRMED_BUGS = newEventType(
                                                                    "bugzilla_unconfirmed_bugs",
                                                                    Severity.INFO);
    
    public static EventType EVENT_BUGZILLA_NEW_BUGS         = newEventType("bugzilla_new_bugs",
                                                                    Severity.INFO);
    
    public static EventType EVENT_BUGZILLA_ASSIGNED_BUGS    = newEventType(
                                                                    "bugzilla_assigned_bugs",
                                                                    Severity.INFO);
    
    public static EventType EVENT_BUGZILLA_REOPENED_BUGS    = newEventType(
                                                                    "bugzilla_reopened_bugs",
                                                                    Severity.INFO);
    
    public static EventType EVENT_BUGZILLA_READY_BUGS       = newEventType("bugzilla_ready_bugs",
                                                                    Severity.INFO);
    public static EventType EVENT_BUGZILLA_RESOLVED_BUGS    = newEventType(
                                                                    "bugzilla_resolved_bugs",
                                                                    Severity.INFO);
    
    public static EventType EVENT_BUGZILLA_NEW_BUG          = newEventType("bugzilla_new_bug",
                                                                    Severity.INFO);
    
    public static EventType EVENT_BUGZILLA_UPDATED_BUG      = newEventType("bugzilla_updated_bug",
                                                                    Severity.INFO);
    
    public static EventType EVENT_BUGZILLA_REMINDER_BUG     = newEventType("bugzilla_reminder",
                                                                    Severity.INFO);
    
    
    
    /**
     * Returns the bugzilla provider.
     * 
     * @return
     */
    public static Provider bugzillaProvider() {
    
    
        final Provider provider = new Provider();
        provider.setIcon("bugzilla");
        provider.setName("bugzilla");
        provider.setProviderType(ProviderType.BUGTRACKER);
        provider.setUrl("bugzilla://");
        return provider;
        
    }
    
    
    public static Provider jenkinsProvider() {
    
    
        final Provider provider = new Provider();
        provider.setIcon("jenkins");
        provider.setName("jenkins");
        provider.setProviderType(ProviderType.CI_BUILD);
        provider.setUrl("jenkins://");
        return provider;
        
    }
    
    
    public static Provider perforceProvider() {
    
    
        final Provider provider = new Provider();
        provider.setIcon("perforce");
        provider.setName("perforce");
        provider.setProviderType(ProviderType.SCM);
        provider.setUrl("perforce://");
        return provider;
        
    }
    
    
    public static Provider sonarProvider() {
    
    
        final Provider provider = new Provider();
        provider.setIcon("sonar");
        provider.setName("sonar");
        provider.setProviderType(ProviderType.QUALITY);
        provider.setUrl("sonar://");
        return provider;
        
    }
    
    
    /**
     * Returns the bugzilla provider.
     * 
     * @return
     */
    public static Provider testLinkProvider() {
    
    
        final Provider provider = new Provider();
        provider.setIcon("testlink");
        provider.setName("testlink");
        provider.setProviderType(ProviderType.REQUIREMENTS);
        provider.setUrl("testlink://");
        return provider;
        
    }
    
    
    private static EventType newEventType(final String _eventKey, final Severity _info) {
    
    
        final EventType eventType = new EventType();
        eventType.setEventKey(_eventKey);
        eventType.setSeverity(_info);
        return eventType;
    }
    
    
    public EsperEventFactory() {
    
    
        super();
    }
    
    
}
