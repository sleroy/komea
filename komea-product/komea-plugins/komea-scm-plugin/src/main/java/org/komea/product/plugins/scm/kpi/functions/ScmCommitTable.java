/**
 * 
 */

package org.komea.product.plugins.scm.kpi.functions;



import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.komea.product.plugins.scm.api.plugin.IScmCommit;
import org.komea.product.plugins.scm.utils.IScmCommitGroupingFunction;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;



/**
 * @author sleroy
 */
public class ScmCommitTable<TKey>
{
    
    
    /**
     * Returns a table grouped by a key from a commit list.
     * 
     * @param _key
     *            the key
     * @param _commitGroupingFunction
     *            the commit function.
     * @return the list of commits;
     */
    public static <TKey2> ScmCommitTable<TKey2> buildTableFromCommitsAndKey(
            final Collection<IScmCommit> _commits,
            final IScmCommitGroupingFunction<TKey2> _commitGroupingFunction) {
    
    
        final ScmCommitTable<TKey2> scmCommitPerDayTable = new ScmCommitTable<TKey2>();
        for (final IScmCommit commit : _commits) {
            scmCommitPerDayTable.addCommit(commit, _commitGroupingFunction.getKey(commit));
        }
        return scmCommitPerDayTable;
        
    }
    
    
    
    private final Multimap<TKey, IScmCommit> commitTable = HashMultimap.create();
    
    
    
    public ScmCommitTable() {
    
    
        super();
    }
    
    
    /**
     * Adds a new commit, the date of the commit is stripped from its hour/minutes/seconds
     */
    public void addCommit(final IScmCommit _scmCommit, final TKey _key) {
    
    
        Validate.notNull(_scmCommit);
        Validate.notNull(_scmCommit.getCommitTime());
        
        commitTable.put(_key, _scmCommit);
        
    }
    
    
    /**
     * Returns the list of commits performed that day
     * 
     * @param _periodDate
     *            the day
     * @return the list of commits or an empty list.
     */
    public Collection<IScmCommit> getListOfCommitsPerKey(final TKey _periodDate) {
    
    
        final Collection<IScmCommit> collection = commitTable.get(_periodDate);
        
        if (collection == null) { return Collections.EMPTY_LIST; }
        return collection;
    }
    
    
    /**
     * Returns the multimap containing all the commits per day.
     * 
     * @return the commit table.
     */
    public Multimap<TKey, IScmCommit> getCommitTable() {
    
    
        return commitTable;
    }
    
    
    /**
     * Returns the number of days stored in the table.
     * 
     * @return the number of days
     */
    public int getNumberOfKeys() {
    
    
        return commitTable.keys().size();
    }
    
    
    /**
     * Returns the list of days stored into the table.
     * 
     * @return the list of days.
     */
    public Set<TKey> keys() {
    
    
        return commitTable.keySet();
    }
    
    
}
