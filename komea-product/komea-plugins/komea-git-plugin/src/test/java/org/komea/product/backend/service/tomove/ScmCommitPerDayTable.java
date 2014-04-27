/**
 * 
 */

package org.komea.product.backend.service.tomove;



import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.komea.product.plugins.git.utils.IScmCommitGroupingFunction;
import org.komea.product.plugins.scm.api.plugin.IScmCommit;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;



/**
 * @author sleroy
 */
public class ScmCommitPerDayTable<TKey>
{
    
    
    private final Multimap<TKey, IScmCommit> commitTable = HashMultimap.create();
    
    
    
    public ScmCommitPerDayTable() {
    
    
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
    public Collection<IScmCommit> getCommitsOfTheDay(final TKey _periodDate) {
    
    
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
    public int getNumberOfDays() {
    
    
        return commitTable.keys().size();
    }
    
    
    /**
     * Returns a table grouped by a key from a commit list.
     * 
     * @param _key
     *            the key
     * @param _commitGroupingFunction
     *            the commit function.
     * @return the list of commits;
     */
    public <TKey2> ScmCommitPerDayTable<TKey2> buildTableFromCommitsAndKey(
            final TKey _key,
            final IScmCommitGroupingFunction<TKey2> _commitGroupingFunction) {
    
    
        final Collection<IScmCommit> commitList = commitTable.get(_key);
        final ScmCommitPerDayTable<TKey2> scmCommitPerDayTable = new ScmCommitPerDayTable<TKey2>();
        for (final IScmCommit commit : commitList) {
            scmCommitPerDayTable.addCommit(commit, _commitGroupingFunction.getKey(commit));
        }
        return scmCommitPerDayTable;
        
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
