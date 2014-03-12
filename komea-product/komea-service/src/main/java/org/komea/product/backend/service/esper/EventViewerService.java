// $codepro.audit.disable

package org.komea.product.backend.service.esper;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.plugin.api.Properties;
import org.komea.product.backend.plugin.api.Property;
import org.komea.product.backend.service.ISettingListener;
import org.komea.product.backend.service.ISettingService;
import org.komea.product.cep.api.ICEPQuery;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.enums.RetentionPeriod;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.Setting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 */
@Service
@Properties({
        @Property(
                key = EventViewerService.RETENTION_EVENT_INFO,
                description = "The retention time for events with severity INFO",
                type = RetentionPeriod.class,
                value = "ONE_HOUR"),
        @Property(
                key = EventViewerService.RETENTION_EVENT_MINOR,
                description = "The retention time for events with severity MINOR",
                type = RetentionPeriod.class,
                value = "SIX_HOURS"),
        @Property(
                key = EventViewerService.RETENTION_EVENT_MAJOR,
                description = "The retention time for events with severity MAJOR",
                type = RetentionPeriod.class,
                value = "ONE_DAY"),
        @Property(
                key = EventViewerService.RETENTION_EVENT_CRITICAL,
                description = "The retention time for events with severity CRITICAL",
                type = RetentionPeriod.class,
                value = "ONE_WEEK"),
        @Property(
                key = EventViewerService.RETENTION_EVENT_BLOCKER,
                description = "The retention time for events with severity BLOCKER",
                type = RetentionPeriod.class,
                value = "ONE_MONTH") })
public class EventViewerService implements IEventViewerService, ISettingListener
{
    
    
    public static final String      RETENTION_EVENT_BLOCKER  = "retention_event_blocker";
    public static final String      RETENTION_EVENT_CRITICAL = "retention_event_critical";
    public static final String      RETENTION_EVENT_INFO     = "retention_event_info";
    public static final String      RETENTION_EVENT_MAJOR    = "retention_event_major";
    public static final String      RETENTION_EVENT_MINOR    = "retention_event_minor";
    
    
    private static final Logger     LOGGER                   = LoggerFactory
                                                                     .getLogger("event-viewer");
    @Autowired
    private IEventEngineService     esperService;
    
    
    private final RetentionPeriod[] lastRetentionPeriods     = new RetentionPeriod[Severity
                                                                     .values().length];
    
    
    @Autowired
    private ISettingService         settingService;
    
    
    
    /**
     * Build retention query
     * 
     * @param _severity
     *            the severity
     * @return the esper query.
     */
    public String buildRetentionQuery(final Severity _severity) {
    
    
        return new RetentionQueryBuilder(_severity, getRetentionTime(_severity)).build();
    }
    
    
    /**
     * Method getEsperService.
     * 
     * @return IEventEngineService
     */
    public final IEventEngineService getEsperService() {
    
    
        return esperService;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.esper.IEventViewerService#getGlobalActivity()
     */
    @Override
    public List<IEvent> getGlobalActivity() {
    
    
        final List<IEvent> blockingList = getInstantView(RETENTION_EVENT_BLOCKER);
        final List<IEvent> criticalList = getInstantView(RETENTION_EVENT_CRITICAL);
        final List<IEvent> majorList = getInstantView(RETENTION_EVENT_MAJOR);
        final List<IEvent> minorList = getInstantView(RETENTION_EVENT_MINOR);
        final List<IEvent> infoList = getInstantView(RETENTION_EVENT_INFO);
        final List<IEvent> events =
                new ArrayList<IEvent>(blockingList.size()
                        + criticalList.size() + majorList.size() + minorList.size()
                        + infoList.size());
        events.addAll(blockingList);
        events.addAll(criticalList);
        events.addAll(majorList);
        events.addAll(minorList);
        events.addAll(infoList);
        Collections.sort(events, new EventDateComparator());
        return events;
    }
    
    
    /**
     * Method getInstantView.
     * 
     * @param _EplStatement
     *            String
     * @return List<IEvent>
     * @see org.komea.product.backend.service.esper.IEventViewerService#getInstantView(String)
     */
    @Override
    public List<IEvent> getInstantView(final String _EplStatement) {
    
    
        final ICEPQuery requiredEplStatement = esperService.getStatementOrFail(_EplStatement);
        return CEPQueryResultConvertor.build(requiredEplStatement).listUnderlyingObjects();
    }
    
    
    public ISettingService getSettingService() {
    
    
        return settingService;
    }
    
    
    /**
     * Initialize the service.
     */
    @PostConstruct
    public void initialize() {
    
    
        settingService.registerListener(RETENTION_EVENT_BLOCKER, this);
        settingService.registerListener(RETENTION_EVENT_CRITICAL, this);
        settingService.registerListener(RETENTION_EVENT_MAJOR, this);
        settingService.registerListener(RETENTION_EVENT_MINOR, this);
        settingService.registerListener(RETENTION_EVENT_INFO, this);
        // Force update at least the first time
        notifyPropertyChanged(null);
    }
    
    
    @Override
    public void notifyPropertyChanged(final Setting _setting) {
    
    
        updateIfNecessaryEventStream(Severity.BLOCKER, RETENTION_EVENT_BLOCKER);
        updateIfNecessaryEventStream(Severity.CRITICAL, RETENTION_EVENT_CRITICAL);
        updateIfNecessaryEventStream(Severity.MAJOR, RETENTION_EVENT_MAJOR);
        updateIfNecessaryEventStream(Severity.MINOR, RETENTION_EVENT_MINOR);
        updateIfNecessaryEventStream(Severity.INFO, RETENTION_EVENT_INFO);
        
        
    }
    
    
    /**
     * Method setEsperService.
     * 
     * @param _esperService
     *            IEventEngineService
     */
    public final void setEsperService(final IEventEngineService _esperService) {
    
    
        esperService = _esperService;
    }
    
    
    public void setSettingService(final ISettingService _settingService) {
    
    
        settingService = _settingService;
    }
    
    
    /**
     * Returns the retention time.
     * 
     * @param _severity
     *            the severity
     * @return the retention period.
     */
    private RetentionPeriod getRetentionTime(final Severity _severity) {
    
    
        switch (_severity) {
            case BLOCKER:
                return interrogateServerSettingsBase(RETENTION_EVENT_BLOCKER);
            case CRITICAL:
                return interrogateServerSettingsBase(RETENTION_EVENT_CRITICAL);
            case MAJOR:
                return interrogateServerSettingsBase(RETENTION_EVENT_MAJOR);
            case MINOR:
                return interrogateServerSettingsBase(RETENTION_EVENT_MINOR);
            case INFO:
                return interrogateServerSettingsBase(RETENTION_EVENT_INFO);
            default:
                return null;
        }
        
    }
    
    
    /**
     * Tests if the value has changed for a retention period.
     * 
     * @param _severity
     * @return true if the value has changed.
     */
    private boolean hasChanged(final Severity _severity) {
    
    
        final RetentionPeriod lastRetentionPeriod = lastRetentionPeriods[_severity.ordinal()];
        final RetentionPeriod retentionTime = getRetentionTime(_severity);
        return lastRetentionPeriod != retentionTime;
        
    }
    
    
    private RetentionPeriod interrogateServerSettingsBase(final String _retentionEventBlocker) {
    
    
        return settingService.<RetentionPeriod> getValueOrNull(_retentionEventBlocker);
        
    }
    
    
    private void updateIfNecessaryEventStream(final Severity _severity, final String _queryName) {
    
    
        if (hasChanged(_severity)) {
            lastRetentionPeriods[_severity.ordinal()] = getRetentionTime(_severity);
            LOGGER.info("Upgrading Stream of events {} with new retention policy", _severity);
            esperService.createEPL(new QueryDefinition(buildRetentionQuery(_severity), _queryName));
        }
    }
}
