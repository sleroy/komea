/**
 *
 */

package org.komea.product.plugins.bugzilla.service.backup;



import org.junit.Test;
import org.komea.product.plugins.bugzilla.api.IBZConfigurationDAO;
import org.komea.product.plugins.bugzilla.api.IBugZillaRebuildHistory;
import org.komea.product.plugins.bugzilla.datasource.BugZillaDataSourceTest;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * @author sleroy
 */
public class BugZillaRebuildHistoryServiceTest extends AbstractSpringIntegrationTestCase
{


    @Autowired
    private IBZConfigurationDAO     BZConfigurationDAO;
    
    @Autowired
    private IBugZillaRebuildHistory bzRebuildHistoryService;



    /**
     * Test method for {@link org.komea.product.plugins.bugzilla.service.backup.BZRebuildHistoryService#rebuildHistory()}.
     */
    @Test
    public final void testRebuildHistory() throws Exception {


        BZConfigurationDAO.saveOrUpdate(BugZillaDataSourceTest.fakeConfiguration());
        bzRebuildHistoryService.rebuildHistory();
    }

}
