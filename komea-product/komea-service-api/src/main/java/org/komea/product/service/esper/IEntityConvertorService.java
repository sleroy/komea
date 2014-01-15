
package org.komea.product.service.esper;


import java.util.List;

/**
 * This interface defines a convertor from an entity to a dTO and reverse way.
 * 
 * @author sleroy
 * @param <T1>
 * @param <T2>
 */
public interface IEntityConvertorService<T1, T2>
{
    
    public T1 convertDTO(T2 _object);
    
    public List<T1> convertDTOs(List<T2> _objects);
    
    public List<T2> convertEntities(List<T1> _objects);
    
    public T2 convertEntity(T1 _object);
    
}
