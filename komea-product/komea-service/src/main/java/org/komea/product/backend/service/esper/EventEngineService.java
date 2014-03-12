/**
 * 
 */

package org.komea.product.backend.service.esper;



import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang.Validate;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.exceptions.CEPQueryNotFoundException;
import org.komea.product.backend.exceptions.InvalidQueryDefinitionException;
import org.komea.product.cep.CEPConfiguration;
import org.komea.product.cep.CEPEngine;
import org.komea.product.cep.api.ICEPEngine;
import org.komea.product.cep.api.ICEPQuery;
import org.komea.product.database.alert.IEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.stereotype.Service;



/**
 * This type defines the esper engine bean. It initializes the Esper Engine.
 * 
 * @author sleroy
 * @version $Revision: 1.0 $
 */
@Service
public final class EventEngineService implements IEventEngineService
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger("komea-esper");
    
    private ICEPEngine          cepEngine;
    
    
    
    /**
     * Method createEPL.
     * 
     * @param _queryDefinition
     *            IQueryDefinition
     * @return EPStatement
     * @throws RuntimeException
     * @see org.komea.product.backend.api.IEventEngineService#createEPL(IQueryDefinition)
     */
    @Override
    public ICEPQuery createEPL(final IQueryDefinition _queryDefinition) throws RuntimeException {
    
    
        Validate.notNull(_queryDefinition);
        LOGGER.debug("Creation of a new EPL Statement {}", _queryDefinition);
        try {
            final ICEPQuery query =
                    (ICEPQuery) BeanUtils.instantiateClass(Thread.currentThread()
                            .getContextClassLoader().loadClass(_queryDefinition.getQuery()));
            cepEngine.getQueryAdministration().registerQuery(_queryDefinition.getName(), query);
            return query;
        } catch (final Exception e) {
            LOGGER.error("Query invalid : " + _queryDefinition, e);
            throw new InvalidQueryDefinitionException(_queryDefinition, e);
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
     * @see org.komea.product.backend.api.IEventEngineService#createOrUpdateEPLQuery(IQueryDefinition)
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
     * @see org.komea.product.backend.api.IEventEngineService#existEPL(String)
     */
    @Override
    public boolean existEPL(final String _metricKey) {
    
    
        return cepEngine.getQueryAdministration().existQuery(_metricKey);
    }
    
    
    /**
     * Returns the cep engine.
     * 
     * @return the cep engine.
     */
    public ICEPEngine getCepEngine() {
    
    
        return cepEngine;
    }
    
    
    /**
     * Method getStatement.
     * 
     * @param _statementName
     *            String
     * @return EPStatement
     * @see org.komea.product.backend.api.IEventEngineService#getStatement(String)
     */
    @Override
    public ICEPQuery getStatement(final String _statementName) {
    
    
        LOGGER.trace("Requesting esper statement {}", _statementName);
        Validate.notEmpty(_statementName);
        return cepEngine.getQueryAdministration().getQuery(_statementName);
    }
    
    
    /**
     * Method getStatementNames.
     * 
     * @return String[]
     * @see org.komea.product.backend.api.IEventEngineService#getStatementNames()
     */
    @Override
    public String[] getStatementNames() {
    
    
        return cepEngine.getQueryAdministration().getQueryNames().toArray(null);
    }
    
    
    /**
     * Method getStatementOrFail.
     * 
     * @param _measureName
     *            String
     * @return EPStatement
     * @see org.komea.product.backend.api.IEventEngineService#getStatementOrFail(String)
     */
    @Override
    public ICEPQuery getStatementOrFail(final String _measureName) {
    
    
        Validate.notEmpty(_measureName);
        final ICEPQuery statement = getStatement(_measureName);
        if (statement == null) { throw new CEPQueryNotFoundException(_measureName); }
        return statement;
    }
    
    
    /*
     * (non-Javadoc)
     * @see com.tocea.scertify.ci.flow.bean.IEsperEngine#init()
     */
    @PostConstruct
    public void init() {
    
    
        LOGGER.debug("Initialization of CEP Engine");
        cepEngine = new CEPEngine(new CEPConfiguration());
        try {
            cepEngine.initialize();
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
    
    
    public void setCepEngine(final ICEPEngine _cepEngine) {
    
    
        cepEngine = _cepEngine;
    }
    
}
