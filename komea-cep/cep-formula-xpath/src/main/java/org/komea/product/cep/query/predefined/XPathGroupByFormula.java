/**
 * 
 */

package org.komea.product.cep.query.predefined;



import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import org.komea.eventory.api.formula.ICEPResult;
import org.komea.eventory.api.formula.ITupleResultMap;
import org.komea.eventory.api.formula.tuple.IEventGroup;
import org.komea.eventory.api.formula.tuple.IEventTable;
import org.komea.eventory.api.formula.tuple.ITuple;
import org.komea.eventory.api.formula.tuple.ITuplerFormula;
import org.komea.eventory.formula.tuple.TupleResultMap;
import org.komea.product.cep.formula.XPathFormula;
import org.komea.product.cep.query.xpath.XPathTree;



/**
 * This class defines a xpath group by formula.
 * 
 * @author sleroy
 */
public class XPathGroupByFormula implements ITuplerFormula
{
    
    
    private final XPathFormula formula;
    
    
    
    public XPathGroupByFormula(final String _xpath) {
    
    
        formula = new XPathFormula(_xpath);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.formula.tuple.ITuplerFormula#processMap(org.komea.eventory.api.formula.tuple.IEventTable,
     * java.util.Map)
     */
    @Override
    public ITupleResultMap processMap(final IEventTable _tupleMap, final Map _ownParameters) {
    
    
        final TupleResultMap tupleResultMap = new TupleResultMap<Object>();
        for (final Entry<ITuple, IEventGroup> entry : _tupleMap.iterator()) {
            
            final XPathTree xPathTree = new XPathTree(entry.getValue());
            
            final ICEPResult executeQuery =
                    formula.executeQuery(Collections.<String, Object> emptyMap(), xPathTree);
            
            tupleResultMap.insertEntry(entry.getKey(), executeQuery.asType());
            
        }
        
        return tupleResultMap;
    }
}
