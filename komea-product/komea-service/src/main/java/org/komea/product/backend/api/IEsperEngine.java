
package org.komea.product.backend.api;



import com.espertech.esper.client.EPServiceProvider;



public interface IEsperEngine
{
    
    
    /**
     * Returns the espere engine service provider.
     * 
     * @return the esper engine service provider.
     */
    EPServiceProvider getEsper();
    
}
