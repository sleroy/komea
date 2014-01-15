/**
 * 
 */

package org.komea.product.backend.service.esper;



import java.util.Date;

import javax.annotation.PostConstruct;

import org.komea.product.backend.api.IEPLMetric;
import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.backend.esper.listeners.EPServiceStateListener1;
import org.komea.product.backend.esper.listeners.EPStatementStateListener1;
import org.komea.product.backend.esper.reactor.EsperDebugReactorListener;
import org.komea.product.database.alert.Alert;
import org.komea.product.database.alert.IAlert;
import org.komea.product.database.alert.enums.Criticity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@Service()
public final class EsperEngineBean implements IAlertService, IEsperEngine
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EsperEngineBean.class);
    
    private EPServiceProvider   esperEngine;
    
    @Autowired
    private AlertValidationBean validator;
    
    
    
    /**
     * Esper Engine BEAN.
     */
    public EsperEngineBean() {
    
    
        super();
        
    }
    
    
    public EPStatement createEPL(final String _name, final String _query) {
    
    
        LOGGER.info("Creation of a new EPL Statement {}->{}", _name, _query);
        final EPStatement createEPL = esperEngine.getEPAdministrator().createEPL(_query, _name);
        
        return createEPL;
    }
    
    
    public IEPLMetric createMetric(final String _name, final String _query) {
    
    
        final EPStatement createEPL = createEPL(_name, _query);
        
        return new EPMetric(createEPL);
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
    
    
    public AlertValidationBean getValidator() {
    
    
        return validator;
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
    public void sendEvent(final IAlert _alert) {
    
    
        validator.validate(_alert);
        sendEventWithoutValidation(_alert);
        
    }
    
    
    @Override
    public void sendEventWithoutValidation(final IAlert _alert) {
    
    
        if (_alert.getDate() == null) {
            _alert.setDate(new Date());
        }
        esperEngine.getEPRuntime().sendEvent(_alert);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * com.tocea.scertify.ci.flow.bean.IEsperEngine#setEsperEngine(com.espertech
     * .esper.client.EPServiceProvider)
     */
    public void setEsperEngine(final EPServiceProvider esperEngine) {
    
    
        this.esperEngine = esperEngine;
    }
    
    
    public void setValidator(final AlertValidationBean _validator) {
    
    
        validator = _validator;
    }
    
}
