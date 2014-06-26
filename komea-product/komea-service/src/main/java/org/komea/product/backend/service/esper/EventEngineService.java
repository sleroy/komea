/**
 *
 */

package org.komea.product.backend.service.esper;



import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.Validate;
import org.komea.eventory.CEPEngine;
import org.komea.eventory.api.engine.ICEPEngine;
import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.eventory.api.engine.IQuery;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.api.IQueryFromInformationsFactory;
import org.komea.product.backend.api.IQueryInformations;
import org.komea.product.backend.api.exceptions.CEPQueryNotFoundException;
import org.komea.product.backend.api.exceptions.InvalidQueryDefinitionException;
import org.komea.product.backend.service.fs.IKomeaFS;
import org.komea.product.backend.service.kpi.FormulaID;
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
    
    
    private static final Logger           LOGGER = LoggerFactory.getLogger("komea-esper");
    
    private ICEPEngine                    cepEngine;
    
    
    /*
     * (non-Javadoc)
     * @see com.tocea.scertify.ci.flow.bean.IEsperEngine#getEsperEngine()
     */
    
    @Autowired
    private IKomeaFS                      komeaFS;
    
    @Autowired
    private IQueryFromInformationsFactory queryFromInformationsFactory;
    
    
    
    /**
     * Method createOrUpdateEPLQuery.
     *
     * @param _definition
     *            IQueryDefinition
     * @see org.komea.product.backend.api.IEventEngineService#createOrUpdateQuery(IQueryInformations)
     */
    @Override
    public void createOrUpdateQuery(final IQueryInformations _definition) {
    
    
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
     * @param _queryInformations
     *            IQueryDefinition
     * @return EPStatement
     * @throws RuntimeException
     * @see org.komea.product.backend.api.IEventEngineService#createQuery(IQueryInformations)
     */
    @Override
    public IQuery createQuery(final IQueryInformations _queryInformations) throws RuntimeException {
    
    
        Validate.notNull(_queryInformations);
        LOGGER.debug("Instantiating and registering query in the CEP Engine {}", _queryInformations);
        try {
            cepEngine.getQueryAdministration().registerQuery(
                    _queryInformations.getQueryName().getId(),
                    _queryInformations.getImplementation());
            return _queryInformations.getImplementation();
        } catch (final Exception e) {
            LOGGER.error("Query invalid : " + _queryInformations, e);
            throw new InvalidQueryDefinitionException(_queryInformations.getImplementation(), e);
        }
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.api.IEventEngineService#createQueryFromInformations(java.lang.String,w
     * org.komea.product.backend.service.esper.stats.AlertPerSeverityPerDay)
     */
    @Override
    public void createQueryFromInformations(
            final FormulaID _queryName,
            final ICEPQueryImplementation _queryImpl) {
    
    
        createOrUpdateQuery(queryFromInformationsFactory.newCEPQuery(_queryName, _queryImpl));
        
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
    public boolean existQuery(final FormulaID _metricKey) {
    
    
        return cepEngine.getQueryAdministration().existQuery(_metricKey.getId());
    }


    /**
     * Returns the cep engine.
     *
     * @return the cep engine.
     */
    public ICEPEngine getCepEngine() {
    
    
        return cepEngine;
    }
    
    
    public IKomeaFS getKomeaFS() {
    
    
        return komeaFS;
    }
    
    
    /**
     * Method getStatement.
     *
     * @param _formulaID
     *            String
     * @return EPStatement
     * @see org.komea.product.backend.api.IEventEngineService#getQuery(String)
     */
    @Override
    public <T extends IQuery> T getQuery(final FormulaID _formulaID) {
    
    
        LOGGER.trace("Requesting esper statement {}", _formulaID);
        Validate.isTrue(_formulaID.isValid());
        return (T) cepEngine.getQueryAdministration().getQuery(_formulaID.getId());
        
    }
    
    
    /**
     * Method getStatementNames.
     *
     * @return String[]
     * @see org.komea.product.backend.api.IEventEngineService#getQueryNames()
     */
    @Override
    public FormulaID[] getQueryNames() {
    
    
        final List<String> queryNames = cepEngine.getQueryAdministration().getQueryNames();
        final FormulaID[] array = new FormulaID[queryNames.size()];
        for (int i = 0; i < queryNames.size(); ++i) {
            array[i] = FormulaID.ofRawID(queryNames.get(i));
        }
        return array;
    }
    
    
    /**
     * Method getStatementOrFail.
     *
     * @param _formulaID
     *            String
     * @return EPStatement
     * @see org.komea.product.backend.api.IEventEngineService#getQueryOrFail(String)
     */
    @Override
    public <T extends IQuery> T getQueryOrFail(final FormulaID _formulaID) {
    
    
        Validate.isTrue(_formulaID.isValid());
        final T statement = getQuery(_formulaID);
        if (statement == null) {
            throw new CEPQueryNotFoundException(_formulaID);
        }
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
            cepEngine.initialize();
        } catch (final IOException e) {
            throw new FatalBeanException("CEP Engine could not started, it crashed");
        }
        
        LOGGER.debug("Complex event processing engine created : " + cepEngine);
        
    }
    
    
    @Override
    public void removeQuery(final FormulaID _formula) {
    
    
        cepEngine.getQueryAdministration().removeQuery(_formula.getId());
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.backend.api.IEventEngineService#sendCustomEvent(java
     * .lang.Object)
     */
    @Override
    public void sendCustomEvent(final Serializable _customEvent) {
    
    
        cepEngine.pushEvent(_customEvent);
        
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
    
    
    public void setKomeaFS(final IKomeaFS _komeaFS) {
    
    
        komeaFS = _komeaFS;
    }
    
}
