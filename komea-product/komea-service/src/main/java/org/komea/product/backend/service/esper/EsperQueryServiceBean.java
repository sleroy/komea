
package org.komea.product.backend.service.esper;



import org.springframework.stereotype.Service;



@Service
public class EsperQueryServiceBean implements IEsperQueryService
{
    
    
    public EsperQueryServiceBean() {
    
    
        super();
    }
    
    
    @Override
    public String[] getStatementNames() {
    
    
        return new String[0];
    }
    
    
    @Override
    public void registerQuery(final String _query, final String _statementName) {
    
    
        // TODO Auto-generated method stub
        
    }
    
}
