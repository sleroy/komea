/**
 * 
 */

package org.komea.product.plugins.scm.kpi.functions;



import java.util.Collection;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.komea.product.plugins.scm.api.plugin.IScmCommit;
import org.komea.product.plugins.scm.kpi.ScmUserQueryImplementation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class AverageCommitMessageLength extends ScmUserQueryImplementation
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AverageCommitMessageLength.class);
    
    
    
    /**
     * @param _commitsOfTheDay
     */
    public AverageCommitMessageLength() {
    
    
    }
    
    
    @Override
    public double compute(final Collection<IScmCommit> commitsOfTheDay) {
    
    
        LOGGER.info("Received {} commits");
        final DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
        
        
        for (final IScmCommit commit : commitsOfTheDay) {
            descriptiveStatistics.addValue(commit.getMessage().length());
        }
        final double mean = descriptiveStatistics.getMean();
        LOGGER.info("Result is {}", mean);
        
        return mean;
        
        
    }
    
    
}
