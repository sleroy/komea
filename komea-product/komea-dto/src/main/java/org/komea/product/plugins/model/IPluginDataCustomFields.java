/**
 *
 */

package org.komea.product.plugins.model;



import java.io.Serializable;
import java.util.Map;



/**
 * @author sleroy
 */
public interface IPluginDataCustomFields extends Serializable
{
    
    
    boolean containsField(String _field);
    
    
    /**
     * Returns the field
     *
     * @param _name
     *            the field name
     * @return the field.
     */
    Serializable getField(String _name);
    
    
    Map<String, Serializable> getFieldsAsMap();
    
    
    /**
     * Returns the fields injected into a pojo.
     *
     * @return the fields injected into a pojo
     */
    <T> T getPojo(Class<T> _pojoClass);
}
