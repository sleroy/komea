/**
 * 
 */

package org.komea.product.plugins.scm.kpi;



import java.util.Collection;
import java.util.List;

import org.komea.eventory.api.engine.ICEPStatement;
import org.komea.eventory.api.formula.ICEPFormula;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.plugins.scm.api.plugin.ICommitFunction;
import org.komea.product.plugins.scm.api.plugin.IScmCommit;
import org.komea.product.plugins.scm.kpi.functions.ScmCommitTable;
import org.komea.product.plugins.scm.utils.IScmCommitGroupingFunction;
import org.komea.product.service.dto.EntityKey;



/**
 * This generic formula computes statistics per user based on the commits
 * received.
 * 
 * @author sleroy
 */
public class CommitFormula implements ICEPFormula<IScmCommit, KpiResult>
{
    
    
    private final ICommitFunction                       commitFunction;
    private final IScmCommitGroupingFunction<EntityKey> groupingFunction;
    
    
    
    /**
     * @param _commitFunction
     */
    public CommitFormula(
            final IScmCommitGroupingFunction<EntityKey> _groupingFunction,
            final ICommitFunction _commitFunction) {
    
    
        groupingFunction = _groupingFunction;
        commitFunction = _commitFunction;
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.cep.api.formula.ICEPFormula#compute(org.komea.eventory
     * .api.engine.ICEPStatement, java.util.Map)
     */
    @Override
    public KpiResult compute(final ICEPStatement<IScmCommit> _arg0) {
    
    
        final List<IScmCommit> aggregateView = _arg0.getAggregateView();
        final KpiResult kpiResult = new KpiResult();
        final ScmCommitTable<EntityKey> scmCommitPerDayTable =
                ScmCommitTable.buildTableFromCommitsAndKey(aggregateView, groupingFunction);
        
        for (final EntityKey entityKey : scmCommitPerDayTable.keys()) {
            final Collection<IScmCommit> userCommits =
                    scmCommitPerDayTable.getListOfCommitsPerKey(entityKey);
            kpiResult.put(entityKey, commitFunction.compute(userCommits));
        }
        
        return kpiResult;
    }
}
