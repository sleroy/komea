
package org.komea.product.backend.service.esper;



import org.komea.product.backend.api.IEsperEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class EsperQueryServiceBean implements IEsperQueryService
{
    
    
    @Autowired
    private IEsperEngine        esperEngineService;
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EsperQueryServiceBean.class);
    
    
    
    public EsperQueryServiceBean() {
    
    
        super();
    }
    
    
    @Override
    public String[] getStatementNames() {
    
    
        return esperEngineService.getStatementNames();
    }
    
    
    @Override
    public void registerQuery(final String _query, final String _statementName) {
    
    
        LOGGER.info("Registering an esper query {} : {}", _query, _statementName);
        if (esperEngineService.existEPL(_query)) {
            LOGGER.info("--> Replacing an esper query {} : {}", _query, _statementName);
            esperEngineService.createEPL(_statementName, _query);
            return;
        }
        LOGGER.info("--> Creating a new esper query {} : {}", _query, _statementName);
        esperEngineService.createEPL(_statementName, _query);
    }


    public IEsperEngine getEsperEngineService() {
    
    
        return esperEngineService;
    }


    public void setEsperEngineService(IEsperEngine _esperEngineService) {
    
    
        esperEngineService = _esperEngineService;
    }
    
    
}
