/**
 *
 */

package org.komea.product.plugins.mantis.core;



import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.komea.product.backend.service.dataplugin.IDynamicDataSourcePool;
import org.komea.product.plugins.bugtracking.model.IIssue;
import org.komea.product.plugins.bugtracking.model.IIssuePlugin;
import org.komea.product.plugins.mantis.api.IMantisConfigurationDAO;
import org.komea.product.plugins.mantis.model.MantisServerConfiguration;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertFalse;



/**
 * @author sleroy
 */
public class MantisConfigurationIT extends AbstractSpringIntegrationTestCase
{


    @Autowired
    private IMantisConfigurationDAO mantisConfigurationDAO;


    @Autowired
    private IDynamicDataSourcePool  pool;



    @Test
    public void test() {


        mantisConfigurationDAO.saveOrUpdate(configuration());
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
    private MantisServerConfiguration configuration() {


        final MantisServerConfiguration serv = new MantisServerConfiguration();
        serv.setAddress("http://bugreport.tocea.fr/api/soap/mantisconnect.php");
        serv.setLogin("demoit");
        serv.setPassword("demoit");
        return serv;
    }
}
