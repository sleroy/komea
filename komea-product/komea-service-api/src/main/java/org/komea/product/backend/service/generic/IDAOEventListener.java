/**
 * 
 */

package org.komea.product.backend.service.generic;



import java.io.Serializable;



/**
 * The DAO Event listener
 * 
 * @author sleroy
 */
public interface IDAOEventListener<T, PK extends Serializable>
{
    
    
    public void notifyDelete(PK _entityKey);
    
    
    public void notifyUpdate(T _entity);
    
    
}
