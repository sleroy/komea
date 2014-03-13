/**
 * 
 */

package org.komea.product.cep.formula.tuple;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jodd.bean.BeanUtil;

import org.apache.commons.lang.Validate;
import org.komea.product.cep.api.ITupleResultMap;
import org.komea.product.cep.api.formula.tuple.ITuple;
import org.springframework.beans.BeanUtils;



/**
 * This class defines a tuple/result map
 * 
 * @author sleroy
 */
public class TupleResultMap<TRes> implements ITupleResultMap<TRes>
{
    
    
    /**
     * 
     */
    private static final String     VALUE     = "value";
    
    
    private final Map<ITuple, TRes> resultMap = new HashMap<ITuple, TRes>();
    
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ITupleResultMap#asPojoMap(java.lang.Class)
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
     * @see org.komea.product.cep.api.ITupleResultMap#asPojoRows(java.lang.Class)
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
     * @see org.komea.product.cep.api.ITupleResultMap#asSimplifiedMap()
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
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ITupleResultMap#asTupleMap()
     */
    @Override
    public <T> Map<ITuple, TRes> asTupleMap() {
    
    
        return resultMap;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ITupleResultMap#asTupleRows()
     */
    @Override
    public <T> List<ITuple> asTupleRows() {
    
    
        final List<ITuple> tuples = new ArrayList<ITuple>(resultMap.size());
        for (final Entry<ITuple, TRes> entry : resultMap.entrySet()) {
            final ITuple tuple = entry.getKey();
            tuples.add(tuple.append(entry.getValue()));
        }
        return tuples;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ITupleResultMap#get(java.lang.Object)
     */
    @Override
    public TRes get(final Object _key) {
    
    
        return resultMap.get(new ArrayListTuple(new String[] {}, _key));
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ITupleResultMap#getValue(org.komea.product.service.dto.EntityKey)
     */
    @Override
    public TRes getValue(final ITuple _valueInTuple) {
    
    
        return resultMap.get(_valueInTuple);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ITupleResultMap#insertEntry(org.komea.product.cep.api.formula.tuple.ITuple, java.lang.Object)
     */
    @Override
    public void insertEntry(final ITuple _key, final TRes _result) {
    
    
        resultMap.put(_key, _result);
        
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
    
    
        final T pojo = BeanUtils.instantiate(_rowPojo);
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
