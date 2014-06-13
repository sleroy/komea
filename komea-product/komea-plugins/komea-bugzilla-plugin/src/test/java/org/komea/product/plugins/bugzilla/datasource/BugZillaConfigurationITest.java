/**
 *
 */

package org.komea.product.plugins.bugzilla.datasource;



import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.komea.product.backend.service.dataplugin.IDynamicDataSourcePool;
import org.komea.product.plugins.bugtracking.model.IIssue;
import org.komea.product.plugins.bugtracking.model.IIssuePlugin;
import org.komea.product.plugins.bugzilla.api.IBZConfigurationDAO;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertFalse;



/**
 * @author sleroy
 */
public class BugZillaConfigurationITest extends AbstractSpringIntegrationTestCase
{


    @Autowired
    private IBZConfigurationDAO    bzConfigurationDAO;


    @Autowired
    private IDynamicDataSourcePool pool;



    @Test
    public void test() {
    
    
        bzConfigurationDAO.saveOrUpdate(configuration());
        final Collection<IIssuePlugin> dataSourceOfType =
                pool.getDataSourceOfType(IIssuePlugin.class);
        for (final IIssuePlugin plugin : dataSourceOfType) {
            final List<IIssue> data2 = plugin.getData();
            for (final IIssue data : data2) {
                System.out.println(data.getSeverity());
                System.out.println(data.getResolution());
                System.out.println(data.getHandler());
                System.out.println(data.getReporter());
                System.out.println(data.getResolution());
            }
            System.out.println(data2);
            assertFalse(data2.isEmpty());
        }
        assertFalse(dataSourceOfType.isEmpty());
    }


    /**
     * @return
     */
    private BZServerConfiguration configuration() {


        final BZServerConfiguration bzServerConfiguration = new BZServerConfiguration();

        bzServerConfiguration.setAddress("https://bugzilla.softathome.com/bugzilla");
        bzServerConfiguration.setLogin("sylvain.leroy@tocea.com");
        bzServerConfiguration.setPassword("Pyz17Xgt");
        bzServerConfiguration.setReminderAlert(10);

        return bzServerConfiguration;
    }
}
