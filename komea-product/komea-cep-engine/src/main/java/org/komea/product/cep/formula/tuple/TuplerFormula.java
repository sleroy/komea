/**
 * 
 */

package org.komea.product.cep.formula.tuple;



import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.komea.product.cep.api.ICEPFormula;
import org.komea.product.cep.api.ICEPResult;
import org.komea.product.cep.api.ICEPStatement;
import org.komea.product.cep.api.ITupleResultMap;
import org.komea.product.cep.api.formula.tuple.ICEPTuplerFormula;
import org.komea.product.cep.api.formula.tuple.IEventTupler;
import org.komea.product.cep.api.formula.tuple.ITupleCreator;
import org.komea.product.cep.api.formula.tuple.ITupleMap;
import org.komea.product.cep.query.CEPResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * This class defines a formula with tuple
 * 
 * @author sleroy
 */
@SuppressWarnings("rawtypes")
public class TuplerFormula implements ICEPFormula<Serializable>
{
    
    
    private static final Logger        LOGGER = LoggerFactory.getLogger("tupler-formula");
    
    
    private final ICEPTuplerFormula<?> formulaTupler;
    
    
    private final ITupleCreator        tupleCreator;
    
    
    
    /**
     * @param _parameters
     */
    public TuplerFormula(final ITupleCreator _tupleCreator, final ICEPTuplerFormula _formulaTupler) {
    
    
        super();
        tupleCreator = _tupleCreator;
        formulaTupler = _formulaTupler;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPFormula#compute(org.komea.product.cep.api.ICEPStatement, java.util.Map)
     */
    @Override
    public ICEPResult compute(
            final ICEPStatement<Serializable> _statement,
            final Map<String, Object> _parameters) {
    
    
        LOGGER.debug("preparing events to grouping...");
        final IEventTupler eventTupler = new EventStreamToTupleMapProcessor(tupleCreator);
        final List<Serializable> defaultStorage = _statement.getDefaultStorage();
        LOGGER.debug("grouping {} events", defaultStorage.size());
        final ITupleMap tupleMap = eventTupler.generateTupleMap(defaultStorage);
        LOGGER.debug("tuple map produced {}", tupleMap);
        final ITupleResultMap<?> resultMap = formulaTupler.processMap(tupleMap, _parameters);
        LOGGER.debug("resultMap produced {}", resultMap);
        return CEPResult.buildFromMap(resultMap);
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "TuplerFormula [formulaTupler="
                + formulaTupler + ", tupleCreator=" + tupleCreator + "]";
    }
    
    
}
