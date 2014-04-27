/**
 * 
 */

package org.komea.product.plugins.scm.api;



import org.komea.product.database.model.Kpi;



/**
 * @author sleroy
 */
public interface IScmKpiPlugin
{
    
    
    public Kpi averageCommitMessageLength();
    
    
    public Kpi numberOfAddedLinesPerUser();
    
    
    public Kpi numberOfChangedFilesPerDayPerUser();
    
    
    public Kpi numberOfChangedLinesPerDayPerUser();
    
    
    public Kpi numberOfCommitsPerDayPerUser();
    
    
    public Kpi numberofDeletedLinesPerDayPerUser();
    
    
    public Kpi numberTotalOfModifiedLinesPerUser();
    
}
