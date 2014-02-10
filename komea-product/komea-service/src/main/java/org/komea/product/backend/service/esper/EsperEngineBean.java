/**
 * 
 */

package org.komea.product.backend.service.esper;



import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.backend.esper.listeners.EPServiceStateListener1;
import org.komea.product.backend.esper.listeners.EPStatementStateListener1;
import org.komea.product.backend.exceptions.EsperStatementNotFoundException;
import org.komea.product.backend.service.business.IQueryDefinition;
import org.komea.product.database.alert.Event;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.alert.enums.Criticity;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.EventCategory;
import org.komea.product.database.enums.EvictionType;
import org.komea.product.database.enums.Operator;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;



/**
 * This type defines the esper engine bean. It initializes the Esper Engine.
 * 
 * @author sleroy
 */
@Service
public final class EsperEngineBean implements IEsperEngine
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EsperEngineBean.class);
    
    private EPServiceProvider   esperEngine;
    
    
    
    /**
     * Esper Engine BEAN.
     */
    public EsperEngineBean() {
    
    
        super();
        
    }
    
    
    @Override
    public EPStatement createEPL(final IQueryDefinition _queryDefinition) {
    
    
        LOGGER.info("Creation of a new EPL Statement {}", _queryDefinition);
        final EPStatement createEPL =
                esperEngine.getEPAdministrator().createEPL(_queryDefinition.getQuery(),
                        _queryDefinition.getName());
        return createEPL;
    }
    
    
    /*
     * (non-Javadoc)
     * @see com.tocea.scertify.ci.flow.bean.IEsperEngine#getEsperEngine()
     */
    
    @Override
    public void createOrUpdateEPLQuery(final IQueryDefinition _definition) {
    
    
        LOGGER.info("Registering an esper query {} : {}", _definition.getQuery(),
                _definition.getName());
        if (existEPL(_definition.getQuery())) {
            LOGGER.info("--> Replacing an esper query {} : {}", _definition.getQuery(),
                    _definition.getName());
            createEPL(_definition);
            return;
        }
        LOGGER.info("--> Creating a new esper query {} : {}", _definition.getQuery(),
                _definition.getName());
        createEPL(_definition);
    }
    
    
    @PreDestroy
    public void destroy() {
    
    
        LOGGER.warn("-----------------------------------");
        LOGGER.warn("Destroying the esper Engine");
        esperEngine.destroy();
        esperEngine = null;
    }
    
    
    @Override
    public boolean existEPL(final String _metricKey) {
    
    
        return esperEngine.getEPAdministrator().getStatement(_metricKey) != null;
    }
    
    
    @Override
    public EPServiceProvider getEsper() {
    
    
        return esperEngine;
    }
    
    
    @Override
    public EPStatement getStatement(final String _statementName) {
    
    
        LOGGER.trace("Requesting esper statement {}", _statementName);
        
        return esperEngine.getEPAdministrator().getStatement(_statementName);
    }
    
    
    @Override
    public String[] getStatementNames() {
    
    
        return esperEngine.getEPAdministrator().getStatementNames();
    }
    
    
    @Override
    public EPStatement getStatementOrFail(final String _measureName) {
    
    
        final EPStatement statement = getStatement(_measureName);
        if (statement == null) { throw new EsperStatementNotFoundException(_measureName); }
        return statement;
    }
    
    
    /*
     * (non-Javadoc)
     * @see com.tocea.scertify.ci.flow.bean.IEsperEngine#init()
     */
    @PostConstruct
    public void init() {
    
    
        LOGGER.info("Initialization of Esper Engine");
        final Configuration config = new Configuration();
        config.getEngineDefaults().getExecution().setPrioritized(true);
        
        config.addImport(Criticity.class);
        // config.getEngineDefaults().getThreading()
        // .setInternalTimerEnabled(false);
        config.addEventTypeAutoName("com.tocea.scertify.ci.flow.model");
        config.setMetricsReportingEnabled();
        config.addEventType(IEvent.class);
        config.addEventType(Event.class);
        config.addImport(Criticity.class);
        config.addImport(EntityType.class);
        config.addImport(EventCategory.class);
        config.addImport(EvictionType.class);
        config.addImport(Operator.class);
        config.addImport(ProviderType.class);
        config.addImport(Severity.class);
        config.addImport(ValueDirection.class);
        config.addImport(ValueType.class);
        
        
        esperEngine = EPServiceProviderManager.getDefaultProvider(config);
        esperEngine.addServiceStateListener(new EPServiceStateListener1());
        esperEngine.addStatementStateListener(new EPStatementStateListener1());
        LOGGER.info("Esper engine created : " + esperEngine);
        LOGGER.info("Creation of notification listener");
        final EsperDebugReactorListener esperDebugReactorListener = new EsperDebugReactorListener();
        esperDebugReactorListener.setEsperEngine(this);
        esperDebugReactorListener.init();
        LOGGER.info("Esper notification listener created");
    }
    
    
    @Override
    public void sendAlert(final IEvent _event) {
    
    
        // LOGGER.trace("Sending alert {}", _alert);
        
        esperEngine.getEPRuntime().sendEvent(_event);
        
    }
    
}
