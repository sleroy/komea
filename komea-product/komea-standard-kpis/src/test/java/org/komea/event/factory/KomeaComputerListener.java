
package org.komea.event.factory;



import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.EventCategory;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.EventType;



public class KomeaComputerListener implements Serializable
{
    
    public static final EventType       JOB_CONFIGURATION_CHANGED =
            createEventType(
                    "job_configuration_changed",
                    "Jenkins job configuration changed",
                    "",
                    Severity.CRITICAL);
    public static final EventType       BUILD_BROKEN              = createEventType("build_broken",
                                                                          "Jenkins build broken",
                                                                          "", Severity.CRITICAL,
                                                                          EntityType.PERSON);
    public static final EventType       BUILD_CODE_CHANGED        =
                                                                          createEventType(
                                                                                  "build_code_changed",
                                                                                  "Jenkins build with code changes",
                                                                                  "",
                                                                                  Severity.INFO,
                                                                                  EntityType.PERSON);
    public static final EventType       BUILD_COMPLETE            = createEventType(
                                                                          "build_complete",
                                                                          "Jenkins build complete",
                                                                          "", Severity.INFO);
    public static final EventType       BUILD_FAILED              = createEventType("build_failed",
                                                                          "Jenkins build failed",
                                                                          "", Severity.BLOCKER);
    public static final EventType       BUILD_FIXED               = createEventType("build_fixed",
                                                                          "Jenkins build fixed",
                                                                          "", Severity.INFO,
                                                                          EntityType.PERSON);
    public static final EventType       BUILD_INDUSTRIALIZATION   =
                                                                          createEventType(
                                                                                  "build_industrialization",
                                                                                  "Jenkins build industrialization",
                                                                                  "", Severity.INFO);
    public static final EventType       BUILD_INTERRUPTED         =
                                                                          createEventType(
                                                                                  "build_interrupted",
                                                                                  "Jenkins build interrupted",
                                                                                  "",
                                                                                  Severity.MAJOR);
    public static final EventType       BUILD_STARTED             = createEventType(
                                                                          "build_started",
                                                                          "Jenkins build started",
                                                                          "", Severity.INFO);
    public static final EventType       BUILD_STARTED_BY_USER     =
                                                                          createEventType(
                                                                                  "build_started_by_user",
                                                                                  "Jenkins build started by user",
                                                                                  "",
                                                                                  Severity.INFO,
                                                                                  EntityType.PERSON);
    public static final EventType       BUILD_UNSTABLE            = createEventType(
                                                                          "build_unstable",
                                                                          "Jenkins build unstable",
                                                                          "", Severity.CRITICAL);
    public static final List<EventType> EVENT_TYPES               =
                                                                          Arrays.asList(
                                                                                  BUILD_STARTED,
                                                                                  BUILD_INDUSTRIALIZATION,
                                                                                  BUILD_COMPLETE,
                                                                                  BUILD_FAILED,
                                                                                  BUILD_INTERRUPTED,
                                                                                  BUILD_UNSTABLE,
                                                                                  BUILD_CODE_CHANGED,
                                                                                  BUILD_BROKEN,
                                                                                  BUILD_FIXED,
                                                                                  JOB_CONFIGURATION_CHANGED,
                                                                                  BUILD_STARTED_BY_USER);
 
    private static final Logger         LOGGER                    =
                                                                          Logger.getLogger(KomeaComputerListener.class
                                                                                  .getName());
    
    
    
    public static EventType createEventType(
            final String key,
            final String name,
            final String description,
            final Severity severity) {
    
    
        return createEventType(key, name, description, severity, EntityType.PROJECT);
    }
    
    
    public static EventType createEventType(
            final String key,
            final String name,
            final String description,
            final Severity severity,
            final EntityType entityType) {
    
    
        final EventType eventType = new EventType();
        eventType.setCategory(EventCategory.BUILD.name());
        eventType.setDescription(description);
        eventType.setEnabled(true);
        eventType.setEntityType(entityType);
        eventType.setEventKey(key);
        eventType.setName(name);
        eventType.setSeverity(severity);
        return eventType;
    }
    
    
}
