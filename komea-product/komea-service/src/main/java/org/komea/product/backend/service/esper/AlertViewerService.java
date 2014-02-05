
package org.komea.product.backend.service.esper;



import java.util.List;

import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.database.alert.IAlert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.SafeIterator;



@Service
public class AlertViewerService implements IAlertViewerService
{
    
    
    @Autowired
    private IEsperEngine esperService;
    
    
    
    public AlertViewerService() {
    
    
        super();
    }
    
    
    public IEsperEngine getEsperService() {
    
    
        return esperService;
    }
    
    
    @Override
    public List<IAlert> getInstantView(final String _EplStatement) {
    
    
        final EPStatement statementOrFail = esperService.getStatementOrFail(_EplStatement);
        final SafeIterator<EventBean> safeIterator = null;
        return EPStatementResult.build(statementOrFail).listUnderlyingObjects();
    }
    
    
    public void setEsperService(final IEsperEngine _esperService) {
    
    
        esperService = _esperService;
    }
    
}
