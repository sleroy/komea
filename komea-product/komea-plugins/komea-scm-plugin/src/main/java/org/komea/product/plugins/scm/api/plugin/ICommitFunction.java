/**
 * 
 */

package org.komea.product.plugins.scm.api.plugin;



import java.util.Collection;



/**
 * @author sleroy
 */
public interface ICommitFunction
{
    
    
    public double compute(Collection<IScmCommit> _listOfCommits);
}
