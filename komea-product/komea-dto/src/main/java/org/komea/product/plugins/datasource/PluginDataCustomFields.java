/**
 * 
 */

package org.komea.product.plugins.datasource;



import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import jodd.bean.BeanUtil;

import org.komea.product.plugins.model.IPluginDataCustomFields;
import org.springframework.beans.BeanUtils;



/**
 * @author sleroy
 */
public class PluginDataCustomFields implements IPluginDataCustomFields
{
    
    
    private final Map<String, String> fields = new HashMap<String, String>();
    
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.model.IPluginDataCustomFields#containsField(java.lang.String)
     */
    @Override
    public boolean containsField(final String _field) {
    
    
        return fields.containsKey(_field);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.model.IPluginDataCustomFields#getField(java.lang.String)
     */
    @Override
    public String getField(final String _name) {
    
    
        final String value = fields.get(_name);
        if (value == null) {
            return "";
        }
        return value;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.model.IPluginDataCustomFields#getFieldsAsMap()
     */
    @Override
    public Map<String, String> getFieldsAsMap() {
    
    
        return Collections.unmodifiableMap(fields);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.model.IPluginDataCustomFields#getPojo(java.lang.Class)
     */
    @Override
    public <T> T getPojo(final Class<T> _pojoClass) {
    
    
        final T bean = BeanUtils.instantiate(_pojoClass);
        for (final Entry<String, String> entry : fields.entrySet()) {
            BeanUtil.setProperty(bean, entry.getKey(), entry.getValue());
        }
        return bean;
    }
    
    
    /**
     * Puts a field into the map.
     * 
     * @param _key
     *            the key
     * @param _value
     *            the value.
     */
    public void put(final String _key, final String _value) {
    
    
        fields.put(_key, _value);
        
    }
    
}
