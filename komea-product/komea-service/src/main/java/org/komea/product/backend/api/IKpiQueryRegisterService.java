
package org.komea.product.backend.api;



import org.komea.product.database.model.Kpi;



public interface IKpiQueryRegisterService
{
    
    
    public abstract void registerQuery(Kpi _kpi, Object queryImplementation);
    
}
