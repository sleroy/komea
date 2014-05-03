/**
 * 
 */

package org.komea.product.plugins.scm.kpi;



import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.komea.eventory.api.engine.ICEPStatement;
import org.komea.eventory.api.formula.ICEPFormula;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.plugins.scm.api.plugin.ICommitFunction;
import org.komea.product.plugins.scm.api.plugin.IScmCommit;
import org.komea.product.plugins.scm.kpi.functions.ScmCommitTable;
import org.komea.product.service.dto.EntityKey;



/**
 * This generic formula computes statistics per user based on the commits received.
 * 
 * @author sleroy
 */
public class UserCommitFormula implements ICEPFormula<IScmCommit, KpiResult>
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
     * @see org.komea.product.cep.api.formula.ICEPFormula#compute(org.komea.eventory.api.engine.ICEPStatement, java.util.Map)
     */
    @Override
    public KpiResult compute(final ICEPStatement<IScmCommit> _arg0, final Map<String, Object> _arg1) {
    
    
        final List<IScmCommit> aggregateView = _arg0.getAggregateView();
        final KpiResult kpiResult = new KpiResult();
        final ScmCommitTable<EntityKey> scmCommitPerDayTable =
                ScmCommitTable.buildTableFromCommitsAndKey(aggregateView,
                        new ScmUserGroupingFunction());
        
        
        for (final EntityKey userKey : scmCommitPerDayTable.keys()) {
            final Collection<IScmCommit> userCommits =
                    scmCommitPerDayTable.getListOfCommitsPerKey(userKey);
            kpiResult.put(userKey, commitFunction.compute(userCommits));
        }
        
        return kpiResult;
    }
}
