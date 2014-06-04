/**
 * 
 */

package org.komea.product.plugins.model;



import java.util.Map;



/**
 * @author sleroy
 */
public interface IPluginDataCustomFields
{
    
    
    boolean containsField(String _field);
    
    
    /**
     * Returns the field
     * 
     * @param _name
     *            the field name
     * @return the field.
     */
    String getField(String _name);
    
    
    Map<String, String> getFieldsAsMap();
    
    
    /**
     * Returns the fields injected into a pojo.
     * 
     * @return the fields injected into a pojo
     */
    <T> T getPojo(Class<T> _pojoClass);
}
