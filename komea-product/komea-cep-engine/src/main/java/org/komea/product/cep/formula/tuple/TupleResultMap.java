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
import org.komea.product.backend.utils.KomeaEntry;
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
    public <T> Map<T, TRes> asPojoMap(final Class<T> _pojoClass) {
    
    
        final Map<T, TRes> map = new HashMap<T, TRes>(resultMap.size());
        for (final Entry<ITuple, TRes> entry : resultMap.entrySet()) {
            final T pojo = BeanUtils.instantiate(_pojoClass);
            BeanUtil.populateBean(pojo, entry.getKey().asMap());
            map.put(pojo, entry.getValue());
        }
        
        return map;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ITupleResultMap#asPojoRows(java.lang.Class)
     */
    @Override
    public <T> List<T> asPojoRows(final Class<T> _rowPojo) {
    
    
        final List<T> arrayList = new ArrayList<T>(resultMap.size());
        for (final ITuple entry : asTupleRows()) {
            final T pojo = BeanUtils.instantiate(_rowPojo);
            BeanUtil.populateBean(pojo, resultMap);
            arrayList.add(pojo);
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
            Validate.isTrue(tuple.isSingleton(), "HashMapTuple must contains only one property");
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
            tuples.add(tuple.append(new KomeaEntry(VALUE, entry.getValue())));
        }
        return tuples;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ITupleResultMap#insertEntry(org.komea.product.cep.api.formula.tuple.ITuple, java.lang.Object)
     */
    @Override
    public void insertEntry(final ITuple _key, final TRes _result) {
    
    
        resultMap.put(_key, _result);
        
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
