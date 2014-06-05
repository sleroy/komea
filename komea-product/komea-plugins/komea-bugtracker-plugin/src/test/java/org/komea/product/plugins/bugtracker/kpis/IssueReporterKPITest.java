/**
 * 
 */

package org.komea.product.plugins.bugtracker.kpis;



import org.junit.runner.RunWith;
import org.komea.eventory.api.cache.BackupDelay;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class IssueReporterKPITest
{
    
    
    @Mock
    private BackupDelay      delay;
    
    
    @Mock
    private String           dynamicSource;
    @InjectMocks
    private IssueReporterKPI issueReporterKPI;
    
}
