/**
 * 
 */

package org.komea.product.backend.service.esper;



import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang.Validate;
import org.komea.eventory.CEPConfiguration;
import org.komea.eventory.CEPEngine;
import org.komea.eventory.api.bridge.IEventBridgeFactory;
import org.komea.eventory.api.cache.ICacheStorageFactory;
import org.komea.eventory.api.engine.ICEPEngine;
import org.komea.eventory.api.engine.ICEPQuery;
import org.komea.eventory.query.CEPQuery;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.api.IQueryDefinition;
import org.komea.product.backend.api.exceptions.CEPQueryNotFoundException;
import org.komea.product.backend.api.exceptions.InvalidQueryDefinitionException;
import org.komea.product.backend.service.fs.IKomeaFS;
import org.komea.product.database.alert.IEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * This type defines the esper engine bean. It initializes the Esper Engine.
 * 
 * @author sleroy
 * @version $Revision: 1.0 $
 */
@Service
@Transactional
public final class EventEngineService implements IEventEngineService
{
    
    
    private static final Logger  LOGGER = LoggerFactory.getLogger("komea-esper");
    
    @Autowired
    private ICacheStorageFactory cacheStorageFactory;
    
    
    private ICEPEngine           cepEngine;
    
    
    /*
     * (non-Javadoc)
     * @see com.tocea.scertify.ci.flow.bean.IEsperEngine#getEsperEngine()
     */
    
    @Autowired
    private IEventBridgeFactory  eventBridgeFactory;
    
    
    @Autowired
    private IKomeaFS             komeaFS;
    
    
    
    /**
     * Method createOrUpdateEPLQuery.
     * 
     * @param _definition
     *            IQueryDefinition
     * @see org.komea.product.backend.api.IEventEngineService#createOrUpdateQuery(IQueryDefinition)
     */
    @Override
    public void createOrUpdateQuery(final IQueryDefinition _definition) {
    
    
        Validate.notNull(_definition);
        LOGGER.debug("Registering an esper query  {}", _definition.getQueryName());
        if (existQuery(_definition.getQueryName())) {
            LOGGER.debug("--> Replacing an esper query  {}", _definition.getQueryName());
        } else {
            LOGGER.debug("--> Creating a new esper query  {}", _definition.getQueryName());
        }
        
        createQuery(_definition);
    }
    
    
    /**
     * Method createEPL.
     * 
     * @param _queryDefinition
     *            IQueryDefinition
     * @return EPStatement
     * @throws RuntimeException
     * @see org.komea.product.backend.api.IEventEngineService#createQuery(IQueryDefinition)
     */
    @Override
    public ICEPQuery createQuery(final IQueryDefinition _queryDefinition) throws RuntimeException {
    
    
        Validate.notNull(_queryDefinition);
        LOGGER.debug("Instantiating and registering query in the CEP Engine {}", _queryDefinition);
        try {
            final ICEPQuery query = new CEPQuery(_queryDefinition.getImplementation());
            cepEngine.getQueryAdministration()
                    .registerQuery(_queryDefinition.getQueryName(), query);
            return query;
        } catch (final Exception e) {
            LOGGER.error("Query invalid : " + _queryDefinition, e);
            throw new InvalidQueryDefinitionException(_queryDefinition.getImplementation(), e);
        }
        
    }
    
    
    @PreDestroy
    public void destroy() {
    
    
        LOGGER.debug("-----------------------------------");
        LOGGER.debug("Destroying the CEP Engine");
        try {
            cepEngine.close();
        } catch (final IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        cepEngine = null;
    }
    
    
    /**
     * Method existEPL.
     * 
     * @param _metricKey
     *            String
     * @return boolean
     * @see org.komea.product.backend.api.IEventEngineService#existQuery(String)
     */
    @Override
    public boolean existQuery(final String _metricKey) {
    
    
        return cepEngine.getQueryAdministration().existQuery(_metricKey);
    }
    
    
    public ICacheStorageFactory getCacheStorageFactory() {
    
    
        return cacheStorageFactory;
    }
    
    
    /**
     * Returns the cep engine.
     * 
     * @return the cep engine.
     */
    public ICEPEngine getCepEngine() {
    
    
        return cepEngine;
    }
    
    
    public IEventBridgeFactory getEventBridgeFactory() {
    
    
        return eventBridgeFactory;
    }
    
    
    public IKomeaFS getKomeaFS() {
    
    
        return komeaFS;
    }
    
    
    /**
     * Method getStatement.
     * 
     * @param _statementName
     *            String
     * @return EPStatement
     * @see org.komea.product.backend.api.IEventEngineService#getQuery(String)
     */
    @Override
    public ICEPQuery getQuery(final String _statementName) {
    
    
        LOGGER.trace("Requesting esper statement {}", _statementName);
        Validate.notEmpty(_statementName);
        return cepEngine.getQueryAdministration().getQuery(_statementName);
    }
    
    
    /**
     * Method getStatementNames.
     * 
     * @return String[]
     * @see org.komea.product.backend.api.IEventEngineService#getQueryNames()
     */
    @Override
    public String[] getQueryNames() {
    
    
        final List<String> queryNames = cepEngine.getQueryAdministration().getQueryNames();
        return queryNames.toArray(new String[queryNames.size()]);
    }
    
    
    /**
     * Method getStatementOrFail.
     * 
     * @param _measureName
     *            String
     * @return EPStatement
     * @see org.komea.product.backend.api.IEventEngineService#getQueryOrFail(String)
     */
    @Override
    public ICEPQuery getQueryOrFail(final String _measureName) {
    
    
        Validate.notEmpty(_measureName);
        final ICEPQuery statement = getQuery(_measureName);
        if (statement == null) { throw new CEPQueryNotFoundException(_measureName); }
        return statement;
    }
    
    
    /*
     * (non-Javadoc)
     * @see com.tocea.scertify.ci.flow.bean.IEsperEngine#init()
     */
    @PostConstruct
    public void init() {
    
    
        cepEngine = new CEPEngine();
        LOGGER.debug("Initialization of CEP Engine");
        try {
            final CEPConfiguration cepConfiguration = new CEPConfiguration();
            cepConfiguration.setBridgeFactory(eventBridgeFactory);
            cepConfiguration.setCacheStorageFactory(cacheStorageFactory);
            cepConfiguration.setStorageFolder(komeaFS.getFileSystemFolder("eventory"));
            cepEngine.initialize(cepConfiguration);
        } catch (final IOException e) {
            throw new FatalBeanException("CEP Engine could not started, it crashed");
        }
        
        
        LOGGER.debug("Complex event processing engine created : " + cepEngine);
        
    }
    
    
    /**
     * Method sendEvent.
     * 
     * @param _event
     *            IEvent
     * @see org.komea.product.backend.api.IEventEngineService#sendEvent(IEvent)
     */
    @Override
    public void sendEvent(final IEvent _event) {
    
    
        cepEngine.pushEvent(_event);
        
    }
    
    
    public void setCacheStorageFactory(final ICacheStorageFactory _cacheStorageFactory) {
    
    
        cacheStorageFactory = _cacheStorageFactory;
    }
    
    
    public void setCepEngine(final ICEPEngine _cepEngine) {
    
    
        cepEngine = _cepEngine;
    }
    
    
    public void setEventBridgeFactory(final IEventBridgeFactory _eventBridgeFactory) {
    
    
        eventBridgeFactory = _eventBridgeFactory;
    }
    
    
    public void setKomeaFS(final IKomeaFS _komeaFS) {
    
    
        komeaFS = _komeaFS;
    }
    
}
