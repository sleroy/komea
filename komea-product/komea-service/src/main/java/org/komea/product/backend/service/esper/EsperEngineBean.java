/**
 * 
 */

package org.komea.product.backend.service.esper;



import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang.Validate;
import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.backend.esper.listeners.EPServiceStateListener1;
import org.komea.product.backend.esper.listeners.EPStatementStateListener1;
import org.komea.product.backend.exceptions.EsperStatementNotFoundException;
import org.komea.product.cep.api.ICEPEngine;
import org.komea.product.database.alert.Event;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.alert.enums.Criticity;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.EventCategory;
import org.komea.product.database.enums.EvictionType;
import org.komea.product.database.enums.Operator;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.RetentionPeriod;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;



/**
 * This type defines the esper engine bean. It initializes the Esper Engine.
 * 
 * @author sleroy
 * @version $Revision: 1.0 $
 */
@Service
public final class EsperEngineBean implements IEsperEngine
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger("komea-esper");
    
    @Autowired
    private ICEPEngine          cepEngine;
    
    
    private EPServiceProvider   esperEngine;
    
    
    
    /**
     * Esper Engine BEAN.
     */
    public EsperEngineBean() {
    
    
        super();
        
    }
    
    
    /**
     * Method createEPL.
     * 
     * @param _queryDefinition
     *            IQueryDefinition
     * @return EPStatement
     * @throws RuntimeException
     * @see org.komea.product.backend.api.IEsperEngine#createEPL(IQueryDefinition)
     */
    @Override
    public EPStatement createEPL(final IQueryDefinition _queryDefinition) throws RuntimeException {
    
    
        Validate.notNull(_queryDefinition);
        LOGGER.debug("Creation of a new EPL Statement {}", _queryDefinition);
        try {
            final EPStatement createEPL =
                    esperEngine.getEPAdministrator().createEPL(_queryDefinition.getQuery(),
                            _queryDefinition.getName());
            return createEPL;
        } catch (final RuntimeException e) {
            LOGGER.error("Query invalid : " + _queryDefinition, e);
            throw e;
        }
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see com.tocea.scertify.ci.flow.bean.IEsperEngine#getEsperEngine()
     */
    
    /**
     * Method createOrUpdateEPLQuery.
     * 
     * @param _definition
     *            IQueryDefinition
     * @see org.komea.product.backend.api.IEsperEngine#createOrUpdateEPLQuery(IQueryDefinition)
     */
    @Override
    public void createOrUpdateEPLQuery(final IQueryDefinition _definition) {
    
    
        Validate.notNull(_definition);
        
        LOGGER.debug("Registering an esper query {} : {}", _definition.getQuery(),
                _definition.getName());
        if (existEPL(_definition.getQuery())) {
            LOGGER.debug("--> Replacing an esper query {} : {}", _definition.getQuery(),
                    _definition.getName());
            createEPL(_definition);
            return;
        }
        LOGGER.debug("--> Creating a new esper query {} : {}", _definition.getQuery(),
                _definition.getName());
        createEPL(_definition);
    }
    
    
    @PreDestroy
    public void destroy() {
    
    
        LOGGER.debug("-----------------------------------");
        LOGGER.debug("Destroying the esper Engine");
        esperEngine.destroy();
        esperEngine = null;
    }
    
    
    /**
     * Method existEPL.
     * 
     * @param _metricKey
     *            String
     * @return boolean
     * @see org.komea.product.backend.api.IEsperEngine#existEPL(String)
     */
    @Override
    public boolean existEPL(final String _metricKey) {
    
    
        return esperEngine.getEPAdministrator().getStatement(_metricKey) != null;
    }
    
    
    public ICEPEngine getCepEngine() {
    
    
        return cepEngine;
    }
    
    
    /**
     * Method getEsper.
     * 
     * @return EPServiceProvider
     * @see org.komea.product.backend.api.IEsperEngine#getEsper()
     */
    @Override
    public EPServiceProvider getEsper() {
    
    
        return esperEngine;
    }
    
    
    /**
     * Method getStatement.
     * 
     * @param _statementName
     *            String
     * @return EPStatement
     * @see org.komea.product.backend.api.IEsperEngine#getStatement(String)
     */
    @Override
    public EPStatement getStatement(final String _statementName) {
    
    
        LOGGER.trace("Requesting esper statement {}", _statementName);
        Validate.notEmpty(_statementName);
        return esperEngine.getEPAdministrator().getStatement(_statementName);
    }
    
    
    /**
     * Method getStatementNames.
     * 
     * @return String[]
     * @see org.komea.product.backend.api.IEsperEngine#getStatementNames()
     */
    @Override
    public String[] getStatementNames() {
    
    
        return esperEngine.getEPAdministrator().getStatementNames();
    }
    
    
    /**
     * Method getStatementOrFail.
     * 
     * @param _measureName
     *            String
     * @return EPStatement
     * @see org.komea.product.backend.api.IEsperEngine#getStatementOrFail(String)
     */
    @Override
    public EPStatement getStatementOrFail(final String _measureName) {
    
    
        Validate.notEmpty(_measureName);
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
    
    
        LOGGER.debug("Initialization of Esper Engine");
        final Configuration config = new Configuration();
        config.getEngineDefaults().getExecution().setPrioritized(true);
        
        // config.getEngineDefaults().getThreading()
        // .setInternalTimerEnabled(false);
        
        config.setMetricsReportingEnabled();
        
        config.addEventTypeAutoName("org.komea.product.database.model");
        config.addEventTypeAutoName("org.komea.product.database.enums");
        
        
        config.addImport(Criticity.class);
        
        
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
        config.addImport(RetentionPeriod.class);
        config.setMetricsReportingEnabled();
        
        
        esperEngine = EPServiceProviderManager.getDefaultProvider(config);
        esperEngine.addServiceStateListener(new EPServiceStateListener1());
        esperEngine.addStatementStateListener(new EPStatementStateListener1());
        LOGGER.debug("Esper engine created : " + esperEngine);
        LOGGER.debug("Creation of notification listener");
        final EsperDebugReactorListener esperDebugReactorListener = new EsperDebugReactorListener();
        esperDebugReactorListener.setEsperEngine(this);
        esperDebugReactorListener.init();
        LOGGER.debug("Esper notification listener created");
    }
    
    
    /**
     * Register an update listener.
     * 
     * @param _updateListener
     *            the update listener.
     * @param _statementName
     *            String
     */
    public void registerListener(final String _statementName, final UpdateListener _updateListener) {
    
    
        Validate.notEmpty(_statementName);
        Validate.notNull(_updateListener);
        LOGGER.debug("Registering listener on {}", _statementName);
        final EPStatement statement = getStatement(_statementName);
        if (statement == null) { throw new EsperStatementNotFoundException(_statementName); }
        statement.addListener(_updateListener);
        
    }
    
    
    /**
     * Method sendEvent.
     * 
     * @param _event
     *            IEvent
     * @see org.komea.product.backend.api.IEsperEngine#sendEvent(IEvent)
     */
    @Override
    public void sendEvent(final IEvent _event) {
    
    
        esperEngine.getEPRuntime().sendEvent(_event);
        
    }
    
    
    public void setCepEngine(final ICEPEngine _cepEngine) {
    
    
        cepEngine = _cepEngine;
    }
    
}
