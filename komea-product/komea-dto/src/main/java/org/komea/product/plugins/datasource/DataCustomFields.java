/**
 *
 */

package org.komea.product.plugins.datasource;



import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.komea.product.backend.utils.PojoUtils;
import org.komea.product.plugins.model.IDataCustomFields;



/**
 * @author sleroy
 */
public class DataCustomFields implements IDataCustomFields
{
    
    
    private Map<String, Serializable> fields = new HashMap<String, Serializable>();
    
    
    
    public DataCustomFields() {
    
    
        super();
    }
    
    
    public DataCustomFields(final Map _attributes) {
    
    
        fields = _attributes;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.model.IDataCustomFields#containsField(java.lang.String)
     */
    @Override
    public boolean containsField(final String _field) {
    
    
        return fields.containsKey(_field);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.model.IDataCustomFields#getField(java.lang.String)
     */
    @Override
    public Serializable getField(final String _name) {
    
    
        return fields.get(_name);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.model.IDataCustomFields#getFieldsAsMap()
     */
    @Override
    public Map<String, Serializable> getFieldsAsMap() {
    
    
        return Collections.unmodifiableMap(fields);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.model.IDataCustomFields#getPojo(java.lang.Class)
     */
    @Override
    public <T> T getPojo(final Class<T> _pojoClass) {
    
    
        return PojoUtils.injectInPojo(_pojoClass, fields);
    }
    
    
    /**
     * Puts a field into the map.
     *
     * @param _key
     *            the key
     * @param _value
     *            the value.
     */
    public void put(final String _key, final Serializable _value) {
    
    
        fields.put(_key, _value);
        
    }
    
}
