
package org.komea.product.backend.service.esper;



import java.util.ArrayList;
import java.util.List;

import org.komea.product.database.alert.IAlert;
import org.springframework.stereotype.Service;



@Service
public class EsperAlertSnapshotService implements IEsperAlertSnapshotService
{
    
    
    public EsperAlertSnapshotService() {
    
    
        super();
    }
    
    
    @Override
    public List<IAlert> getDefaultView() {
    
    
        return new ArrayList<IAlert>();
    }
    
    
    @Override
    public List<IAlert> getInstantView(final String _EplStatement) {
    
    
        return new ArrayList<IAlert>();
    }
    
}
