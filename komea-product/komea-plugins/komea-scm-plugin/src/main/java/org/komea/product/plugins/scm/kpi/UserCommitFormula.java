/**
 * 
 */

package org.komea.product.plugins.scm.kpi;



import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.komea.eventory.api.engine.ICEPStatement;
import org.komea.eventory.api.formula.ICEPFormula;
import org.komea.eventory.api.formula.ICEPResult;
import org.komea.eventory.formula.tuple.TupleFactory;
import org.komea.eventory.formula.tuple.TupleResultMap;
import org.komea.eventory.query.CEPResult;
import org.komea.product.plugins.scm.api.plugin.ICommitFunction;
import org.komea.product.plugins.scm.api.plugin.IScmCommit;
import org.komea.product.plugins.scm.kpi.functions.ScmCommitPerDayTable;
import org.komea.product.service.dto.EntityKey;



/**
 * This generic formula computes statistics per user based on the commits received.
 * 
 * @author sleroy
 */
public class UserCommitFormula implements ICEPFormula<IScmCommit>
{
    
    
    private final ICommitFunction commitFunction;
    
    
    
    /**
     * @param _commitFunction
     */
    public UserCommitFormula(final ICommitFunction _commitFunction) {
    
    
        commitFunction = _commitFunction;
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.formula.ICEPFormula#compute(org.komea.eventory.api.engine.ICEPStatement, java.util.Map)
     */
    @Override
    public ICEPResult compute(final ICEPStatement<IScmCommit> _arg0, final Map<String, Object> _arg1) {
    
    
        final List<IScmCommit> aggregateView = _arg0.getAggregateView();
        final ScmCommitPerDayTable<EntityKey> scmCommitPerDayTable =
                ScmCommitPerDayTable.buildTableFromCommitsAndKey(aggregateView,
                        new ScmUserGroupingFunction());
        
        final TupleResultMap<Double> tupleResultMap = new TupleResultMap<Double>();
        
        for (final EntityKey userKey : scmCommitPerDayTable.keys()) {
            final Collection<IScmCommit> userCommits =
                    scmCommitPerDayTable.getListOfCommitsPerKey(userKey);
            tupleResultMap.insertEntry(TupleFactory.newTuple(userKey),
                    commitFunction.compute(userCommits));
        }
        
        
        return CEPResult.buildFromMap(tupleResultMap);
    }
    
}
