/**
 *
 */

package org.komea.product.plugins.datasource;



import java.io.Serializable;
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


    private final Map<String, Serializable> fields = new HashMap<String, Serializable>();



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
    public Serializable getField(final String _name) {


        return fields.get(_name);
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.model.IPluginDataCustomFields#getFieldsAsMap()
     */
    @Override
    public Map<String, Serializable> getFieldsAsMap() {


        return Collections.unmodifiableMap(fields);
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.model.IPluginDataCustomFields#getPojo(java.lang.Class)
     */
    @Override
    public <T> T getPojo(final Class<T> _pojoClass) {


        final T bean = BeanUtils.instantiate(_pojoClass);
        for (final Entry<String, Serializable> entry : fields.entrySet()) {
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
    public void put(final String _key, final Serializable _value) {


        fields.put(_key, _value);

    }

}
