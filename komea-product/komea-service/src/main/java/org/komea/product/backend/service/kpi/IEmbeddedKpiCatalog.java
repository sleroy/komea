/**
 * 
 */

package org.komea.product.backend.service.kpi;



import java.util.Set;



/**
 * @author sleroy
 *
 */
public interface IEmbeddedKpiCatalog
{
    
    
    public abstract Set<KpiDefinition> getKpiDefinitions();
    
}
