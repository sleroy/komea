/**
 * 
 */

package org.komea.eventory.formula.tuple;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jodd.bean.BeanUtil;

import org.apache.commons.lang.Validate;
import org.komea.eventory.api.formula.ITupleResultMap;
import org.komea.eventory.api.formula.tuple.ITuple;
import org.komea.eventory.utils.ClassUtils;



/**
 * This class defines a tuple/result map
 * 
 * @author sleroy
 */
public class TupleResultMap<TRes> extends HashMapResultMap<ITuple, TRes> implements
        ITupleResultMap<TRes>
{
    
    
    private static final String VALUE = "value";
    
    
    
    public TupleResultMap() {
    
    
        super();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ITupleResultMap#asPojoMap(java.lang.Class)
     */
    @Override
    public <T> Map<T, TRes> asPojoMap(final String[] _fieldSet, final Class<T> _pojoClass) {
    
    
        final Map<T, TRes> map = new HashMap<T, TRes>(resultMap.size());
        for (final Entry<ITuple, TRes> entry : resultMap.entrySet()) {
            map.put(instantiatePojoFromTuple(_pojoClass, _fieldSet, entry.getKey()),
                    entry.getValue());
        }
        
        return map;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ITupleResultMap#asPojoRows(java.lang.Class)
     */
    @Override
    public <T> List<T> asPojoRows(final String[] _fieldSet, final Class<T> _rowPojo) {
    
    
        final List<T> arrayList = new ArrayList<T>(resultMap.size());
        for (final ITuple entry : asTupleRows()) {
            arrayList.add(instantiatePojoFromTuple(_rowPojo, _fieldSet, entry));
        }
        
        return arrayList;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ITupleResultMap#asSimplifiedMap()
     */
    @Override
    public <T> Map<T, TRes> asSimplifiedMap() {
    
    
        final Map<T, TRes> simplfiedMap = new HashMap<T, TRes>();
        for (final Entry<ITuple, TRes> entry : resultMap.entrySet()) {
            final ITuple tuple = entry.getKey();
            Validate.isTrue(tuple.isSingleton(), "Tuple must contains only one property");
            simplfiedMap.put(tuple.<T> getFirst(), entry.getValue());
        }
        return simplfiedMap;
    }
    
    
    @Override
    public List<ITuple> asTupleRows() {
    
    
        final List<ITuple> tuples = new ArrayList<ITuple>(resultMap.size());
        for (final Entry<ITuple, TRes> entry : resultMap.entrySet()) {
            final ITuple tuple = entry.getKey();
            tuples.add(tuple.append(entry.getValue()));
        }
        return tuples;
    }
    
    
    /**
     * Instantiate a pojo from a tuple.
     * 
     * @param _rowPojo
     * @param fieldNameStrings
     * @param arrayList
     * @param entry
     * @return the new pojo.
     */
    public <T> T instantiatePojoFromTuple(
            final Class<T> _rowPojo,
            final String[] fieldNameStrings,
            final ITuple entry) {
    
    
        final T pojo = ClassUtils.instantiate(_rowPojo);
        BeanUtil.populateBean(pojo, entry.asMap(fieldNameStrings));
        return pojo;
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "TupleResultMap [resultMap=" + dumpMap() + "]";
    }
    
    
    /**
     * @return
     */
    private String dumpMap() {
    
    
        final StringBuilder sBuilder = new StringBuilder();
        for (final Entry<ITuple, TRes> entry : resultMap.entrySet()) {
            sBuilder.append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n");
        }
        return sBuilder.toString();
    }
    
}
