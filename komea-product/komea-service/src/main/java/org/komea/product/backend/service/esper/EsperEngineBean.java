/**
 * 
 */

package org.komea.product.backend.service.esper;



import javax.annotation.PostConstruct;

import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.backend.esper.listeners.EPServiceStateListener1;
import org.komea.product.backend.esper.listeners.EPStatementStateListener1;
import org.komea.product.backend.esper.reactor.EsperDebugReactorListener;
import org.komea.product.database.alert.Alert;
import org.komea.product.database.alert.IAlert;
import org.komea.product.database.alert.enums.Criticity;
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
    public EPStatement createEPL(final String _name, final String _query) {
    
    
        LOGGER.info("Creation of a new EPL Statement {}->{}", _name, _query);
        final EPStatement createEPL = esperEngine.getEPAdministrator().createEPL(_query, _name);
        return createEPL;
    }
    
    
    @Override
    public boolean existEPL(final String _metricKey) {
    
    
        return esperEngine.getEPAdministrator().getStatement(_metricKey) != null;
    }
    
    
    /*
     * (non-Javadoc)
     * @see com.tocea.scertify.ci.flow.bean.IEsperEngine#getEsperEngine()
     */
    
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
        config.addEventType(IAlert.class);
        config.addEventType(Alert.class);
        
        
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
    public void sendAlert(final IAlert _alert) {
    
    
        LOGGER.trace("Sending alert {}", _alert);
        
        esperEngine.getEPRuntime().sendEvent(_alert);
        
    }
    
    
    /**
     * Destroy the esper engine.
     */
    public void shutdown() {
    
    
        esperEngine.destroy();
        esperEngine = null;
        
    }
    
}
